/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 *             AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/openAUSIAS
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.daw.dao.specific.implementation;

import net.daw.dao.generic.implementation.TableDaoGenImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import net.daw.bean.specific.implementation.CompraBean;
import net.daw.bean.specific.implementation.ProductoBean;
import net.daw.bean.specific.implementation.TipoproductoBean;
import net.daw.bean.specific.implementation.UserBean;
import net.daw.data.specific.implementation.MysqlDataSpImpl;
//import net.daw.bean.specific.implementation.UsuarioBean;
import net.daw.helper.statics.AppConfigurationHelper;
import static net.daw.helper.statics.MetaEnum.FieldType.Date;

public class UsuarioDao extends TableDaoGenImpl<UserBean> {

    public UsuarioDao(Connection pooledConnection) throws Exception {
        super(pooledConnection);

    }

    public UserBean getFromLogin(UserBean oUsuarioBean) throws Exception {
        try {
            ResultSet existe = oMysql.getAllSql("select * from usuario");
            if (existe != null) {
                while (existe.next()) {
                    if (existe.getString("login").equals(oUsuarioBean.getLogin())) {

                        Date fecha = existe.getDate("fnac");

                        //BUG FECHAS SOLUCIONADO
                        SimpleDateFormat formatter = new SimpleDateFormat("MM");
                        String str_fecha = formatter.format(fecha);
                        String nuevo = existe.getString("password") + str_fecha;
                        if (nuevo.equals(oUsuarioBean.getPassword())) {
                            oUsuarioBean.setId(existe.getInt("id"));
                            oUsuarioBean.setNombre(existe.getString("nombre"));
                            oUsuarioBean.setPrimerapellido(existe.getString("ape1"));
                            oUsuarioBean.setSegundopellido(existe.getString("ape2"));
                            oUsuarioBean.setSexo(existe.getInt("sexo"));
                            oUsuarioBean.setFnac(existe.getDate("fnac"));

                        }

                    }
                }
            }
            return oUsuarioBean;
        } catch (Exception e) {
            throw new Exception("UsuarioDao.getFromLogin: Error: " + e.getMessage());
        }
    }

    public UserBean change(UserBean oUsuarioBean) throws Exception {
        MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);
        if (oUsuarioBean.getId() > 0) {
            ResultSet existe = oMysql.getAllSql("select * from usuario");
            if (existe != null) {
                while (existe.next()) {
                    if (existe.getInt("id") == oUsuarioBean.getId()) {
                        oUsuarioBean.setId(existe.getInt("id"));
                        oUsuarioBean.setNombre(existe.getString("nombre"));
                        oUsuarioBean.setPrimerapellido(existe.getString("ape1"));
                        oUsuarioBean.setSegundopellido(existe.getString("ape2"));
                        oUsuarioBean.setSexo(existe.getInt("sexo"));
                        oUsuarioBean.setFnac(existe.getDate("fnac"));
                        oUsuarioBean.setLogin(existe.getString("login"));
                        oUsuarioBean.setPassword(existe.getString("password"));
                    }
                }
            }
        }

        return oUsuarioBean;
    }

    public int removeproducto(int id) throws Exception {

        ResultSet existe = oMysql.getAllSql("select * from producto");
        int contadorproducto = 0;
        if (existe != null) {
            while (existe.next()) {
                if (existe.getInt("id_tipoproducto") == id) {
                    oMysql.removeOne(existe.getInt("id"), "producto");
                    contadorproducto++;

                }

            }
        }

        return contadorproducto;
    }

    public int removetipoproducto(int id) throws Exception {

        ResultSet existeproducto = oMysql.getAllSql("select * from producto");
        int contadorproducto = 0;
        if (existeproducto != null) {
            while (existeproducto.next()) {
                ResultSet existe = oMysql.getAllSql("select * from compra");
                if (existe != null) {
                    while (existe.next()) {
                        if (existeproducto.getInt("id") == existe.getInt("id_producto") && existeproducto.getInt("id_tipoproducto") == id) {
                            oMysql.removeOne(existe.getInt("id"), "compra");
                            contadorproducto++;
                        }
                    }
                }
            }
        }

        return contadorproducto;
    }

    public ArrayList<Integer> duplicado() throws Exception {

        ArrayList<Integer> alUserBean = new ArrayList<>();

        ResultSet existe = oMysql.getAllSql("select * from usuario");
        int cont = 0;
        if (existe != null) {
            while (existe.next()) {
                ResultSet nuevo = oMysql.getAllSql("select * from usuario");
                cont=0;
                if (nuevo != null) {
                    while (nuevo.next()) {
                        if (existe.getString("nombre").equals(nuevo.getString("nombre")) && existe.getString("ape1").equals(nuevo.getString("ape1")) && existe.getString("ape2").equals(nuevo.getString("ape2"))) {
                            cont++;
                            if(cont>=2){
                                alUserBean.add(existe.getInt("id"));
                            }
                            
                        }
                    }
                }
            }
        }

        return alUserBean;

    }

    public CompraBean comprar(CompraBean oCompraBean) throws Exception {

        MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);

        ResultSet existe = oMysql.getAllSql("select * from producto");
        if (existe != null) {
            while (existe.next()) {
                if (oCompraBean.getId_producto() == existe.getInt("id") && oCompraBean.getCantidad() <= 1000) {
                    if (oCompraBean.getId() == 0) {
                        oCompraBean.setId(oMysql.insertOne("compra"));
                    }
                    oMysql.updateOne(oCompraBean.getId(), "compra", "id", oCompraBean.getId().toString());
                    oMysql.updateOne(oCompraBean.getId(), "compra", "id_producto", oCompraBean.getId_producto().toString());
                    oMysql.updateOne(oCompraBean.getId(), "compra", "id_usuario", oCompraBean.getId_usuario().toString());
                    oMysql.updateOne(oCompraBean.getId(), "compra", "cantidad", oCompraBean.getCantidad().toString());
                    Date date = new Date();
                    //BUG FECHAS SOLUCIONADO
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    oCompraBean.setFnac(date);
                    String str_fecha = formatter.format(oCompraBean.getFnac());
                    oMysql.updateOne(oCompraBean.getId(), "compra", "fnac", str_fecha);
                }
            }
        }
        return oCompraBean;
    }

    public ArrayList<String> tipocomprado(int id) throws Exception {
        ArrayList<String> alarray = new ArrayList<String>();

        ResultSet existe = oMysql.getAllSql("SELECT tipoproducto.descripcion as descripcion FROM producto, tipoproducto, compra WHERE tipoproducto.id = producto.id_tipoproducto AND producto.id = compra.id_producto AND compra.id_usuario=" + id);
        if (existe != null) {
            while (existe.next()) {
                alarray.add(existe.getString("descripcion"));
            }
        }

        return alarray;
    }
}
