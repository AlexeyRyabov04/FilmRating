package by.bsuir.filmrating.controller;

import by.bsuir.filmrating.logic.CommandException;
import by.bsuir.filmrating.logic.CommandHelper;
import by.bsuir.filmrating.logic.ICommand;

import by.bsuir.filmrating.logic.impl.ChangeLanguageCommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Controller() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameterName.COMMAND_NAME);
        ICommand command = CommandHelper.getInstance().getCommand(commandName);
        String page;
        try {
            page = command.execute(request);
        } catch (Exception e) {
            page = JspPageName.ERROR_PAGE;
        }
        if (command instanceof ChangeLanguageCommand) {
            response.sendRedirect(page);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                errorMessageDirectlyFromResponse(response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName =
                request.getParameter(RequestParameterName.COMMAND_NAME);
        ICommand command = CommandHelper.getInstance().getCommand(commandName);
        String page = null;
        try {
            page = command.execute(request);
        } catch (CommandException e) {
            page = JspPageName.ERROR_PAGE;
        } catch (Exception e){
            page = JspPageName.ERROR_PAGE;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null){
            dispatcher.forward(request, response);
        } else{
            errorMessageDirectlyFromResponse(response);
        }
    }
    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws
            IOException{
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }
}