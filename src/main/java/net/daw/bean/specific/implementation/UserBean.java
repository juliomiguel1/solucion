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
package net.daw.bean.specific.implementation;

import com.google.gson.annotations.Expose;
import java.util.Date;
import net.daw.bean.generic.implementation.BeanGenImpl;
import net.daw.bean.publicinterface.BeanInterface;
import net.daw.helper.annotations.MethodMetaInformation;
import net.daw.helper.annotations.TableSourceMetaInformation;
import net.daw.helper.statics.MetaEnum;

/**
 *
 * @author a020864288e
 */
@TableSourceMetaInformation(
        TableName = "usuario",
        Description = "Usuarios del sistema"
)

public class UserBean extends BeanGenImpl implements BeanInterface{

    public UserBean() {
        this.id = 0;
    }

    public UserBean(Integer id) {
        this.id = id;
    }
    
    @Expose
    @MethodMetaInformation(
            IsId = true,
            UltraShortName = "Iden.",
            ShortName = "Identif.",
            Description = "Número Identificador",
            Type = MetaEnum.FieldType.Integer,
            DefaultValue = "0"
    )
    private Integer id;
    
    @Expose
    @MethodMetaInformation(
            UltraShortName = "Nombre",
            ShortName = "Nombre",
            Description = "Nombre usuario",
            Type = MetaEnum.FieldType.String,
            MinLength = 1,
            MaxLength = 255,
            DefaultValue = "Sin Nombre",
            IsForeignKeyDescriptor = true
    )
    private String nombre = "";
    
     @Expose
    @MethodMetaInformation(
            UltraShortName = "primer",
            ShortName = "primer",
            Description = "primer apellido",
            Type = MetaEnum.FieldType.String,
            MinLength = 1,
            MaxLength = 255,
            DefaultValue = "Sin Primer apellido",
            IsForeignKeyDescriptor = true
    )
    private String primerapellido = "";
     
     
     @Expose
    @MethodMetaInformation(
            UltraShortName = "segundo",
            ShortName = "segundo",
            Description = "segundo apellido",
            Type = MetaEnum.FieldType.String,
            MinLength = 1,
            MaxLength = 255,
            DefaultValue = "Sin Segundo apellido",
            IsForeignKeyDescriptor = true
    )
    private String segundopellido = "";
     
     @Expose
    @MethodMetaInformation(
            UltraShortName = "sexo",
            ShortName = "Sexo",
            Description = "Sexo",
            Type = MetaEnum.FieldType.Integer,
            DefaultValue = "0"
    )
    private Integer sexo = 0;
     
      @Expose
    @MethodMetaInformation(
            UltraShortName = "login",
            ShortName = "login",
            Description = "login",
            Type = MetaEnum.FieldType.String,
            MinLength = 1,
            MaxLength = 255,
            DefaultValue = "Sin login",
            IsForeignKeyDescriptor = true
    )
    private String login = "";
      
          @Expose
    @MethodMetaInformation(
            UltraShortName = "Fecha",
            ShortName = "Fecha",
            Description = "Fecha ",
            Type = MetaEnum.FieldType.Date,
            DefaultValue = "01-01-2000",
            IsForeignKeyDescriptor = true
    )
    private Date fnac = new Date();
          
            @Expose
    @MethodMetaInformation(
            UltraShortName = "password",
            ShortName = "password",
            Description = "password",
            Type = MetaEnum.FieldType.String,
            MinLength = 1,
            MaxLength = 255,
            DefaultValue = "password",
            IsForeignKeyDescriptor = true
    )
    private String password = "";

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the primerapellido
     */
    public String getPrimerapellido() {
        return primerapellido;
    }

    /**
     * @param primerapellido the primerapellido to set
     */
    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido;
    }

    /**
     * @return the segundopellido
     */
    public String getSegundopellido() {
        return segundopellido;
    }

    /**
     * @param segundopellido the segundopellido to set
     */
    public void setSegundopellido(String segundopellido) {
        this.segundopellido = segundopellido;
    }

    /**
     * @return the sexo
     */
    public Integer getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the fnac
     */
    public Date getFnac() {
        return fnac;
    }

    /**
     * @param fnac the fnac to set
     */
    public void setFnac(Date fnac) {
        this.fnac = fnac;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
