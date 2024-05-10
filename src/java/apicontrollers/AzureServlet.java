/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package apicontrollers;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Account;
import models.RandomString;

/**
 *
 * @author Sang
 */
@WebServlet(name="AzureServlet", urlPatterns={"/api/azure"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class AzureServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            Part filePart = request.getPart("file");
            if(filePart == null) {
                return;
            }

            String contentType = filePart.getContentType();
            if(contentType == null || !contentType.startsWith("image")) {
                return;
            }

            Account account = AccountDAO.getLoggedInUser(request);

            String connectionString = "DefaultEndpointsProtocol=https;AccountName=lhswebstorage;"
                    + "AccountKey=lkVIkeT/JHiZkyTxyTDUntwQ0px3Ah9xGkTQRBwHnz7HCxdkrKI3Mi1Vs7mvLjD7zNEDSKQnQN/o+AStw6Vh1Q==;"
                    + "EndpointSuffix=core.windows.net";
            String containerName = "todoweb";

            String originalFileName = filePart.getSubmittedFileName();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = account.getUsername() + "-" + RandomString.random(30) + fileExtension;
            String blobFilePath = "user-images/" + fileName;

            //Upload file to Azure Storage
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(blobFilePath);
            
            blobClient.upload(filePart.getInputStream(), filePart.getSize());
            
            if(!account.getImageSrc().isEmpty()) {
                blobClient = containerClient.getBlobClient(account.getImageSrc().replace("https://lhswebstorage.blob.core.windows.net/todoweb/", ""));
                if(blobClient != null) {
                    blobClient.deleteIfExists();
                }
            }
            
            if(AccountDAO.updateImage(account.getUsername(), "https://lhswebstorage.blob.core.windows.net/todoweb/" + blobFilePath)) {
                HttpSession session = request.getSession(false);
                session.removeAttribute("user");
                session.setAttribute("user", AccountDAO.getUser(account.getUsername(), account.getPasswordHash()));
                response.getWriter().print("https://lhswebstorage.blob.core.windows.net/todoweb/" + blobFilePath);
                
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
