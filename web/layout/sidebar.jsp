<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.stodo.dao.CategoriesDAO" %>
<%@page import="com.stodo.models.Category" %>

<% 
    String activeNav = (String) request.getAttribute("ActiveNav");
    
    String user = "admin";
    ArrayList<Category> categories = CategoriesDAO.getUserCategories(user);
%>

<nav class="d-flex flex-column position-relative navigation">
    <div class="nav-header d-flex align-items-center justify-content-between">
        <div class="logo-wrapper d-flex align-items-center">          
            <img src="images/logo-rmbg.webp" alt="Logo" class="logo-img">
            <span class="logo-text">STodo</span>
        </div>

        <div class="mb-toggle-nav">
            <i class="fa-solid fa-bars"></i>
        </div>
    </div>

    <div class="toggle-nav d-flex">
        <i class='bx bxs-chevron-right-circle'></i>
    </div>

    <div class="nav-main d-flex flex-column justify-content-between">
        <div class="nav-contents">
            <div class="nav-item">
                <div class="nav-item-header">
                    <span>Nhiệm vụ</span>
                </div>
                <ul class="nav-item-list">
                    <li>
                        <a class="nav-link d-flex align-items-center justify-content-between <%= activeNav == "dashboard" ? "active" : "" %>" href="/todoapp/dashboard">
                            <div class="nav-link-box d-flex align-items-center">
                                <svg class="nav-icon" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24">
                                    <path d="M3 13h1v7c0 1.103.897 2 2 2h12c1.103 0 2-.897 2-2v-7h1a1 1 0 0 0 .707-1.707l-9-9a.999.999 0 0 0-1.414 0l-9 9A1 1 0 0 0 3 13zm7 7v-5h4v5h-4zm2-15.586 6 6V15l.001 5H16v-5c0-1.103-.897-2-2-2h-4c-1.103 0-2 .897-2 2v5H6v-9.586l6-6z"></path>
                                </svg>

                                <span class="nav-link-text">Tổng quan</span>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a class="nav-link d-flex align-items-center justify-content-between <%= activeNav == "today" ? "active" : "" %>" href="/todoapp/today">
                            <div class="nav-link-box d-flex align-items-center">
                                <svg class="nav-icon" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24">
                                    <path d="M19 4h-3V2h-2v2h-4V2H8v2H5c-1.103 0-2 .897-2 2v14c0 1.103.897 2 2 2h14c1.103 0 2-.897 2-2V6c0-1.103-.897-2-2-2zM5 20V7h14V6l.002 14H5z"></path><path d="m15.628 12.183-1.8-1.799 1.37-1.371 1.8 1.799zm-7.623 4.018V18h1.799l4.976-4.97-1.799-1.799z"></path>
                                </svg>

                                <span class="nav-link-text">Hôm nay</span>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a class="nav-link d-flex align-items-center justify-content-between <%= activeNav == "all" ? "active" : "" %>" href="javascript:void(0)">
                            <div class="nav-link-box d-flex align-items-center">
                                <svg class="nav-icon" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24">
                                    <path d="M19 4h-3V2h-2v2h-4V2H8v2H5c-1.103 0-2 .897-2 2v14c0 1.103.897 2 2 2h14c1.103 0 2-.897 2-2V6c0-1.103-.897-2-2-2zM5 20V7h14V6l.002 14H5z"></path>
                                    <path d="M7 9h10v2H7zm0 4h5v2H7z"></path>
                                </svg>

                                <span class="nav-link-text">Tất cả</span>
                            </div> 
                        </a>
                    </li>
                    <li>
                        <a class="nav-link d-flex align-items-center justify-content-between <%= activeNav == "calendar" ? "active" : "" %>" href="javascript:void(0)">
                            <div class="nav-link-box d-flex align-items-center">
                                <svg class="nav-icon" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24">
                                    <path d="M7 11h2v2H7zm0 4h2v2H7zm4-4h2v2h-2zm0 4h2v2h-2zm4-4h2v2h-2zm0 4h2v2h-2z"></path>
                                    <path d="M5 22h14c1.103 0 2-.897 2-2V6c0-1.103-.897-2-2-2h-2V2h-2v2H9V2H7v2H5c-1.103 0-2 .897-2 2v14c0 1.103.897 2 2 2zM19 8l.001 12H5V8h14z"></path>
                                </svg>

                                <span class="nav-link-text">Lịch</span>
                            </div> 
                        </a>
                    </li>
                    <li>
                        <a class="nav-link d-flex align-items-center justify-content-between <%= activeNav == "notes" ? "active" : "" %>" href="javascript:void(0)">
                            <div class="nav-link-box d-flex align-items-center">
                                <svg class="nav-icon" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24">
                                    <path d="M19 3H5a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h8l8-8V5a2 2 0 0 0-2-2zm-7 16v-7h7l-7 7z"></path>
                                </svg>

                                <span class="nav-link-text">Ghi chú</span>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>

            <hr />

            <div class="nav-item">
                <div class="nav-item-header d-flex align-items-center justify-content-between">
                    <span>Danh mục</span>
                    <a href="javascript:void(0)" class="add-cate-header">
                        <svg class="nav-icon" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                            <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
                        </svg>
                    </a>
                </div>
                <ul class="nav-item-list nav-categories">
                <% for(Category category : categories) { %>
                    <li>
                        <a class="nav-link d-flex align-items-center justify-content-between" href="javascript:void(0)">
                            <div class="nav-link-box d-flex align-items-center">
                                <span class="nav-color-icon" style="background: #e30019;"></span>

                                <span class="nav-link-text"><%= category.getCateName() %></span>
                            </div>

                            <div class="task-count"><%= category.getTodoList().size() %></div>
                        </a>
                    </li>
                <% } %>                  

                    <li>
                        <a href="#" class="add-cate-action d-flex align-items-center">
                            <svg class="nav-icon" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24">
                                <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
                            </svg>

                            Thêm danh mục
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="nav-bottom d-flex flex-column gap-3 pt-2 d-none">
            <a href="login.html" class="nav-bot-user d-flex align-items-center gap-2">
                <div class="nav-user-img-box">
                    <img class="nav-user-img" src="/images/netbian-anime-2.jpg" alt="">
                </div>
                <div class="nav-user-name">
                    <span>Lê Hoàng Sang</span>
                </div>
            </a>
            <a href="javascrip:void(0)" class="nav-bot-setting d-flex align-items-center gap-2">
                <svg xmlns="http://www.w3.org/2000/svg" width="27" height="27" viewBox="0 0 24 24">
                    <path d="M12 16c2.206 0 4-1.794 4-4s-1.794-4-4-4-4 1.794-4 4 1.794 4 4 4zm0-6c1.084 0 2 .916 2 2s-.916 2-2 2-2-.916-2-2 .916-2 2-2z"></path>
                    <path d="m2.845 16.136 1 1.73c.531.917 1.809 1.261 2.73.73l.529-.306A8.1 8.1 0 0 0 9 19.402V20c0 1.103.897 2 2 2h2c1.103 0 2-.897 2-2v-.598a8.132 8.132 0 0 0 1.896-1.111l.529.306c.923.53 2.198.188 2.731-.731l.999-1.729a2.001 2.001 0 0 0-.731-2.732l-.505-.292a7.718 7.718 0 0 0 0-2.224l.505-.292a2.002 2.002 0 0 0 .731-2.732l-.999-1.729c-.531-.92-1.808-1.265-2.731-.732l-.529.306A8.1 8.1 0 0 0 15 4.598V4c0-1.103-.897-2-2-2h-2c-1.103 0-2 .897-2 2v.598a8.132 8.132 0 0 0-1.896 1.111l-.529-.306c-.924-.531-2.2-.187-2.731.732l-.999 1.729a2.001 2.001 0 0 0 .731 2.732l.505.292a7.683 7.683 0 0 0 0 2.223l-.505.292a2.003 2.003 0 0 0-.731 2.733zm3.326-2.758A5.703 5.703 0 0 1 6 12c0-.462.058-.926.17-1.378a.999.999 0 0 0-.47-1.108l-1.123-.65.998-1.729 1.145.662a.997.997 0 0 0 1.188-.142 6.071 6.071 0 0 1 2.384-1.399A1 1 0 0 0 11 5.3V4h2v1.3a1 1 0 0 0 .708.956 6.083 6.083 0 0 1 2.384 1.399.999.999 0 0 0 1.188.142l1.144-.661 1 1.729-1.124.649a1 1 0 0 0-.47 1.108c.112.452.17.916.17 1.378 0 .461-.058.925-.171 1.378a1 1 0 0 0 .471 1.108l1.123.649-.998 1.729-1.145-.661a.996.996 0 0 0-1.188.142 6.071 6.071 0 0 1-2.384 1.399A1 1 0 0 0 13 18.7l.002 1.3H11v-1.3a1 1 0 0 0-.708-.956 6.083 6.083 0 0 1-2.384-1.399.992.992 0 0 0-1.188-.141l-1.144.662-1-1.729 1.124-.651a1 1 0 0 0 .471-1.108z"></path>
                </svg>
                <span>Cài đặt</span>
            </a>
        </div>
    </div>

</nav>