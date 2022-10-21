package com.example.crud.controller;

import com.example.crud.config.TemplateEngineUtil;
import com.example.crud.dao.SupplierDao;
import com.example.crud.implementation.SupplierDaoJDBC;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class SupplierController extends HttpServlet {

    private SupplierDao productSupplierDataStore = SupplierDaoJDBC.getInstance();

    private Map<String, Object> params = createMap();

    public Map<String, Object> getParams() {
        return params;
    }

    private Map<String, Object> createMap() {

        params = new HashMap<String, Object>();
        params.put("supplierList", productSupplierDataStore.getAll());
        return params;
    }

    public SupplierDao getProductSupplierDataStore() {
        return productSupplierDataStore;
    }

    public void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String HTMLpath, Map<String, Object> optionalParameters) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariables(optionalParameters);

        engine.process(HTMLpath, context, resp.getWriter());
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, Object> params = getParams();

        renderTemplate(req, resp, "supplier.html", params);
    }
}
