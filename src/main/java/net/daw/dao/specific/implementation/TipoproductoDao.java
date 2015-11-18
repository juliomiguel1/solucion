/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 * AJAX web applications by using Java and jQuery
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
 * 
 */
package net.daw.dao.specific.implementation;

import java.sql.Connection;
import java.sql.ResultSet;
import net.daw.bean.specific.implementation.ProductoBean;
import net.daw.bean.specific.implementation.TipoproductoBean;
import net.daw.dao.generic.implementation.TableDaoGenImpl;

/**
 *
 * @author a020864288e
 */
public class TipoproductoDao extends TableDaoGenImpl<TipoproductoBean> {

    public TipoproductoDao(Connection pooledConnection) throws Exception {
        super(pooledConnection);
    }

    public int removeproducto(TipoproductoBean oTipoproductoBean) throws Exception {

        ResultSet existe = oMysql.getAllSql("select * from producto");
        int contadorproducto = 0;
        if (existe != null) {
            while (existe.next()) {
                if (existe.getInt("id_tipoproducto") == oTipoproductoBean.getId()) {
                    oMysql.removeOne(oTipoproductoBean.getId(), "producto");
                    contadorproducto++;
                }
                
            }
        }

        return contadorproducto;
    }
    
     public int removetipoproducto(ProductoBean oTipoproductoBean) throws Exception {

        ResultSet existe = oMysql.getAllSql("select * from compra");
        int contadorproducto = 0;
        if (existe != null) {
            while (existe.next()) {
                if (existe.getInt("id_oproducto") == oTipoproductoBean.getId()) {
                    oMysql.removeOne(oTipoproductoBean.getId_tipoproducto(), "compra");
                    contadorproducto++;
                }
            }
        }

        return contadorproducto;
    }
}
