<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="vi_VN" />
<c:if test="${sessionScope.lang == 'en'}">
    <fmt:setLocale value="en_US" />
</c:if>

<fmt:setBundle basename="languages.lang" var="bundle" />

<section class="header-navigation d-flex align-items-center justify-content-between position-relative">
    <form class="search-form d-flex align-items-center position-relative">
        <div class="search-input-box d-flex align-items-center">
            <i class='bx bx-arrow-back d-none btn-close-search'></i>
            <input type="search" id="search" name="search" placeholder="<fmt:message bundle="${bundle}" key="search_placeholder" />" autocomplete="off" required>
        </div>
                        
        <div class="btn-show-search d-none align-items-center">
            <i class='bx bx-search'></i>
        </div>
            
        <div class="search-autocomplete-box">
            <div class="search-autocomplete">
               <div class="d-flex align-items-center justify-content-center p-5 m-5">Kết quả tìm kiếm</div>
            </div>
        </div>
    </form>


    <div class="header-nav-right d-flex align-items-center justify-content-between gap-3">
        <button class="add-new-task s-btn me-2">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
            </svg>

            <span><fmt:message bundle="${bundle}" key="create_task_btn" /></span>
        </button>

        <div class="header-notifications d-flex align-items-center">
            <i class='bx bx-bell header-icon' ></i>
        </div>

        <div class="vertical-break"></div>

        <div class="theme-toggle d-flex align-items-center justify-content-between gap-3">
            <input type="radio" id="light-theme" name="theme">
            <input type="radio" id="dark-theme" name="theme">
            <label for="light-theme"><i class='bx bxs-sun' ></i></label>
            <label for="dark-theme"><i class='bx bxs-moon' ></i></label>
            <div class="theme-selected"></div>                 
        </div>

        <div class="header-settings d-flex align-items-center">
            <a href="setting" class="d-flex align-items-center justify-content-center">
                <i class='bx bx-cog header-icon ${(requestScope.activeHeader == "setting") ? "active" : ""}'></i>
            </a>
        </div>

        <div class="vertical-break"></div>
        
        <c:set var="user" value="${sessionScope.user}" />

        <div class="header-account position-relative">
        <c:choose>
            <c:when test="${(user != null)}">
                <div class="nav-user-img-box" onclick="window.location.href='./setting'">
                    <img class="nav-user-img" src="${(user.imageSrc != null && !user.imageSrc.isBlank() ? user.imageSrc : "images/user-no-image.jpg")}" alt="">
                </div>

                <div class="account-action align-items-center gap-2">
                    <div>
                        <p class="text-nowrap">
                            <fmt:message bundle="${bundle}" key="user_hello" />,&nbsp;${(user.fullName != null ? user.fullName : user.username)}
                        </p>
                        <button class="logout-btn s-btn text-nowrap mt-3" onclick="window.window.location.href = './logout'">Đăng xuất</button>
                    </div>
                </div>
            </c:when>
            <c:when test="${(user == null)}">
                <div class="nav-user-img-box">
                    <img class="nav-user-img" src="images/user-no-image.jpg" alt="">
                </div>
                
                <div class="account-action align-items-center gap-2">
                    <button class="login-btn s-btn text-nowrap" onclick="window.window.location.href = './login'">Đăng nhập</button>
                    <button class="register-btn s-btn text-nowrap" onclick="window.window.location.href = './register'">Đăng ký</button>
                </div>         
            </c:when>
        </c:choose>       
        </div>

        <div class="notifications-wrapper">

        </div>

    </div>
</section>