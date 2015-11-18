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
package net.daw.service.specific.implementation;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specific.implementation.ProductoBean;
import net.daw.bean.specific.implementation.TipoproductoBean;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.dao.specific.implementation.ProductoDao;
import net.daw.dao.specific.implementation.TipoproductoDao;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author a020864288e
 */
public class TipoproductoService extends TableServiceGenImpl {

    public TipoproductoService(HttpServletRequest request) {
        super(request);
    }
    
      public String rm() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        int id = ParameterCook.prepareId(oRequest);
        TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection);
        ProductoDao oProductoDao = new ProductoDao(oConnection);
        ProductoBean oProductoBean = new ProductoBean();
        oProductoBean.setId_tipoproducto(id);
        TipoproductoBean oTipoproductoBean = new TipoproductoBean();
        oTipoproductoBean.setId(id);
        int conttipo =  oTipoproductoDao.removeproducto(oTipoproductoBean);
        int conpro = oTipoproductoDao.removetipoproducto(oProductoBean);
        Map<String, String> data = new HashMap<>();
        data.put("status", "200");
        data.put("message", "tipoproducto= " + ((Integer) id).toString()+", producto"+ conttipo + ", compra"+ conpro );
        Gson gson = new Gson();
        String resultado = gson.toJson(data);
        return resultado;
    }
}
