<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="vi_VN" />
<c:if test="${sessionScope.lang == 'en'}">
    <fmt:setLocale value="en_US" />
</c:if>

<fmt:setBundle basename="languages.lang" var="bundle" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message bundle="${bundle}" key="login" /></title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" 
    integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" 
    integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous" defer></script>

    <!-- Fontawesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
    integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <!-- Box Icon -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

    <!-- CSS -->
    <link type="text/css" rel="stylesheet" href="css/loader.css">
    <link type="text/css" rel="stylesheet" href="css/login.css">
    <link type="text/css" rel="stylesheet" href="css/layout.css">

</head>
<body>

    <jsp:include page="layout/page_loader.jsp" />

    <div class="login-page">
        <div class="login-wrapper d-flex">
            <div class="login-banner">
            </div>
            <div class="login-form-box d-flex flex-column align-items-center justify-content-center">
                <h4 class="text-center mb-4"><fmt:message bundle="${bundle}" key="login" /></h4>
                <% 
                    String error = (String) request.getAttribute("Error");
                    if(error != null && !error.isEmpty()) { 
                %>
                    
                <div class="form-error">
                    <i class="form-error-icon fa-solid fa-circle-exclamation"></i>
                    <span class="form-error-msg"><%= error %></span>
                </div>
                    
                <% } %>
                <form action="login" method="post" class="login-form d-flex flex-column align-items-center">
                    <div class="d-flex flex-column mt-2">
                        <label class="ac-label" for="username"><fmt:message bundle="${bundle}" key="username" /></label>
                        <div class="ac-input-box d-flex align-items-center">
                            <i class='bx bx-user'></i>
                            <input type="text" name="username" id="username" placeholder="<fmt:message bundle="${bundle}" key="username" />" autocomplete="off" required>
                            <span class="ac-input-focus"></span>
                        </div>
                    </div>
                    <div class="d-flex flex-column mt-4">
                        <label class=ac-label for="password"><fmt:message bundle="${bundle}" key="password" /></label>
                        <div class="ac-input-box d-flex align-items-center">
                            <i class='bx bx-lock-alt'></i>
                            <input type="password" name="password" id="password" placeholder="<fmt:message bundle="${bundle}" key="password" />" required>
                            <span class="ac-input-focus"></span>
                        </div>
                    </div>
                    <div class="register-forgot-action d-flex justify-content-between mt-2">
                        <a href="register${(param.lang == 'en') ? "?lang=en" : ""}"><fmt:message bundle="${bundle}" key="register" /></a>
                        <a href="#"><fmt:message bundle="${bundle}" key="forgot_password" />?</a>
                    </div>
                    <button class="ac-button mt-4" type="submit"><fmt:message bundle="${bundle}" key="login" /></button>
                </form>
                <div class="login-break d-flex align-items-center mt-4">
                    <p><fmt:message bundle="${bundle}" key="other_login" /></p>
                </div>
                <div class="other-login-med d-flex gap-3 mt-3">
                    <a href="#">
                        <i class='bx bxl-facebook-circle'></i>
                    </a>
                    <a href="#">
                        <i class='bx bxl-google'></i>
                    </a>
                </div>
            
            </div>
        </div>
    </div>


    <!-- Jquery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.7.1.min.js" defer></script>

    <!-- Javascript -->
    <script type="text/javascript" src="javascript/layout.js" defer></script>

</body>
</html>