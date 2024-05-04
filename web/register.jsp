<%-- 
    Document   : register
    Created on : May 3, 2024, 5:21:30 PM
    Author     : Sang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản</title>

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

    <div class="page-loader show">
        <div aria-label="Orange and tan hamster running in a metal wheel" role="img" class="wheel-and-hamster">
            <div class="wheel"></div>
            <div class="hamster">
                <div class="hamster__body">
                    <div class="hamster__head">
                        <div class="hamster__ear"></div>
                        <div class="hamster__eye"></div>
                        <div class="hamster__nose"></div>
                    </div>
                    <div class="hamster__limb hamster__limb--fr"></div>
                    <div class="hamster__limb hamster__limb--fl"></div>
                    <div class="hamster__limb hamster__limb--br"></div>
                    <div class="hamster__limb hamster__limb--bl"></div>
                    <div class="hamster__tail"></div>
                </div>
            </div>
            <div class="spoke"></div>
        </div>
    </div>

    <div class="register-page">
        <div class="register-wrapper d-flex">
            <div class="register-banner">
            </div>
            <div class="register-form-box d-flex flex-column align-items-center justify-content-center">
                <h4 class="text-center mb-4">Đăng ký tài khoản</h4>
                <form action="register" method="post" class="register-form d-flex flex-column align-items-center">
                    
                <% 
                    String error = (String) request.getAttribute("Error");
                    if(error != null && !error.isEmpty()) { 
                %>
                    
                <div class="form-error">
                    <i class="form-error-icon fa-solid fa-circle-exclamation"></i>
                    <span class="form-error-msg"><%= error %></span>
                </div>
                    
                <% } %>
                    
                    <div class="d-flex flex-column mt-2">
                        <label class="ac-label" for="username">Tài khoản</label>
                        <div class="ac-input-box d-flex align-items-center">
                            <i class='bx bx-user'></i>
                            <input type="text" name="username" id="username" placeholder="Tài khoản" autocomplete="off" required>
                            <span class="ac-input-focus"></span>
                        </div>
                    </div>
                    <div class="d-flex flex-column mt-4">
                        <label class=ac-label for="password">Mật khẩu</label>
                        <div class="ac-input-box d-flex align-items-center">
                            <i class='bx bx-lock-alt'></i>
                            <input type="password" name="password" id="password" placeholder="Mật khẩu" required>
                            <span class="ac-input-focus"></span>
                        </div>
                    </div>
                    <div class="d-flex flex-column mt-4">
                        <label class=ac-label for="confirmpassword">Nhập lại mật khẩu</label>
                        <div class="ac-input-box d-flex align-items-center">
                            <i class='bx bx-lock-alt'></i>
                            <input type="password" name="confirmpassword" id="confirmpassword" placeholder="Nhập lại mật khẩu" required>
                            <span class="ac-input-focus"></span>
                        </div>
                    </div>
                    <div class="register-forgot-action d-flex justify-content-between mt-2">
                        <a href="login">Đăng nhập</a>
                    </div>
                    <button class="ac-button mt-4" type="submit">Đăng ký</button>
                </form>
                <div class="login-break d-flex align-items-center mt-4">
                    <p>Hoặc đăng nhập bằng</p>
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
