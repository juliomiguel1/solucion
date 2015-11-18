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
package net.daw.service.specific.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.daw.service.generic.implementation.TableServiceGenImpl;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specific.implementation.CompraBean;
import net.daw.bean.specific.implementation.ProductoBean;
import net.daw.bean.specific.implementation.TipoproductoBean;
import net.daw.bean.specific.implementation.UserBean;
import net.daw.bean.specific.implementation.UsuarioBean;
//import net.daw.bean.specific.implementation.UsuarioBean;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.specific.implementation.ProductoDao;
import net.daw.dao.specific.implementation.TipoproductoDao;
import net.daw.dao.specific.implementation.UsuarioDao;
import net.daw.helper.statics.AppConfigurationHelper;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.JsonMessage;
import net.daw.helper.statics.ParameterCook;

public class UsuarioService extends TableServiceGenImpl {

    public UsuarioService(HttpServletRequest request) {
        super(request);
    }

    public String login() throws SQLException, Exception {
        UserBean oUserBean = (UserBean) oRequest.getSession().getAttribute("userBean");
        String strAnswer = null;
        String strCode = "OK";
        if (oUserBean == null) {
            String login = oRequest.getParameter("login");
            String pass = oRequest.getParameter("pass");
            if (!login.equals("") && !pass.equals("")) {
                ConnectionInterface DataConnectionSource = null;
                Connection oConnection = null;
                try {
                    DataConnectionSource = new BoneConnectionPoolImpl();
                    oConnection = DataConnectionSource.newConnection();
                    UserBean oUsuario = new UserBean();
                    oUsuario.setLogin(login);
                    oUsuario.setPassword(pass);
                    UsuarioDao oUserDao = new UsuarioDao(oConnection);
                    oUsuario = oUserDao.getFromLogin(oUsuario);
                    if (oUsuario.getId() != 0) {
                        oRequest.getSession().setAttribute("userBean", oUsuario);
                        strAnswer = oUsuario.getLogin();
                    } else {
                        strCode = "KO";
                    }
                } catch (Exception ex) {
                    ExceptionBooster.boost(new Exception(this.getClass().getName() + ":login ERROR " + ex.toString()));
                } finally {
                    if (oConnection != null) {
                        oConnection.close();
                    }
                    if (DataConnectionSource != null) {
                        DataConnectionSource.disposeConnection();
                    }
                }
            }
        } else {
            strAnswer = "Already logged in";
        }
        return JsonMessage.getJsonMsg(strCode, strAnswer);
    }

    public String logout() {
        UserBean oUserBean = (UserBean) oRequest.getSession().getAttribute("userBean");
        if (oUserBean == null) {
            return "{\"status\":KO}";
        } else {
            oRequest.getSession().invalidate();
            return JsonMessage.getJsonMsg("200", "OK");
        }
    }

    public String check()throws Exception {
        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        UserBean oUserBean = (UserBean) oRequest.getSession().getAttribute("userBean");
        ArrayList<String> alarray = new ArrayList<String>();
        if (oUserBean == null) {
            return "{\"status\":KO}";
        } else {
           UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
           alarray = oUsuarioDao.tipocomprado(oUserBean.getId());
            return "{\"status\":OK,\"id\":" + oUserBean.getId() + ", \"nombrecompleto\":" + oUserBean.getNombre() + " " + oUserBean.getPrimerapellido() + " " + oUserBean.getSegundopellido() + ", \"tipoproducto\": " + alarray +"}";
        }
    }

    public String change() throws Exception {

        UserBean oUserBean = (UserBean) oRequest.getSession().getAttribute("userBean");

        String id = oRequest.getParameter("id_usuario");

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        UsuarioDao oPreguntaDao = new UsuarioDao(oConnection);
        int antiguo = oUserBean.getId();
        UserBean onuevoUsuarioBean = new UserBean();
        onuevoUsuarioBean.setId(Integer.parseInt(id));

        onuevoUsuarioBean = oPreguntaDao.change(onuevoUsuarioBean);

        oRequest.getSession().setAttribute("userBean", onuevoUsuarioBean);
        String data;
        if (antiguo == onuevoUsuarioBean.getId()) {
            data = "{\"status\":KO}";
        } else {
            data = "{\"status\":OK}";
        }
        return data;
    }

    public String rm() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        String id = oRequest.getParameter("id_producto");
        UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
        
        int conpro = oUsuarioDao.removetipoproducto(Integer.parseInt(id));
        int conttipo = oUsuarioDao.removeproducto(Integer.parseInt(id));
        String data = "{\"tipoproducto\":"+id+", \"producto\""+conttipo+", \"compra\""+conpro+"}";
        return data;
    }

    public String duplicate() throws Exception {
        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        ArrayList<Integer> alUserBean = new ArrayList<Integer>();
        UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);

        alUserBean = oUsuarioDao.duplicado();

        String data = "{\"id_usuarios\":"+alUserBean+"}";

        return data;
    }

    public String buy() throws Exception {
        UserBean oUserBean = (UserBean) oRequest.getSession().getAttribute("userBean");
        String data = "";
        String id_producto = oRequest.getParameter("id_producto");
        String cantidad = oRequest.getParameter("cantidad");
        if (oUserBean == null || Integer.parseInt(cantidad) > 1000) {
            data = "{\"status\":KO}";
        } else {

            
            Connection oConnection = new BoneConnectionPoolImpl().newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
            CompraBean oCompraBean = new CompraBean();
            oCompraBean.setId_producto(Integer.parseInt(id_producto));
            oCompraBean.setCantidad(Integer.parseInt(cantidad));
            oCompraBean.setId_usuario(oUserBean.getId());
            
            oCompraBean = oUsuarioDao.comprar(oCompraBean);
                        
            data = "{\"status\":OK}";
            
        }
        return data;
    }
}
