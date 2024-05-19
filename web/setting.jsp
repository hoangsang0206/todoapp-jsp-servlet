<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="vi_VN" />
<c:if test="${sessionScope.lang == 'en'}">
    <fmt:setLocale value="en_US" />
</c:if>

<fmt:setBundle basename="languages.lang" var="bundle" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cài đặt</title>
        <jsp:include page="layout/header.jsp"/>
        
        <link type="text/css" rel="stylesheet" href="css/setting.css" />
        <link type="text/css" rel="stylesheet" href="cropperjs/cropper.min.css" />
        
        <script type="text/javascript" src="cropperjs/cropper.min.js" defer></script>
        <script type="text/javascript" src="javascript/setting.js" defer></script>
    </head>
    <body>
        <jsp:include page="layout/page_loader.jsp" />
        <section class="main-layout d-flex">
            <jsp:include page="layout/sidebar.jsp" />
            
            <main>
                <jsp:include page="layout/header_navigation.jsp" />
                
                <c:set var="user" value="${sessionScope.user}" />
                
                <section class="main-contents">
                    <div class="setting">
                        <div class="page-title">Cài đặt <span></span></div>
                        <div class="setting-main d-flex">
                            <div class="setting-side">
                                <div class="setting-side-item active d-flex align-items-center justify-content-between" data-setting="account">
                                    <div>Tài khoản</div>
                                    <i class='bx bx-chevron-right'></i>
                                </div>
                                <div class="setting-side-item d-flex align-items-center justify-content-between" data-setting="languages">
                                    <div>Ngôn ngữ</div>
                                    <i class='bx bx-chevron-right'></i>
                                </div>
                                <div class="setting-side-item d-flex align-items-center justify-content-between" data-setting="other">
                                    <div>Khác</div>
                                    <i class='bx bx-chevron-right'></i>
                                </div>
                            </div>
                            <div class="setting-content">
                                <div class="setting-content-main position-relative">
                                    <div class="setting-content-item current d-flex flex-column gap-4" data-setting="account">
                                        <div class="st-ac-img-box d-flex align-items-center gap-3">
                                            <div class="st-ac-img">
                                                <img src="${(user.imageSrc != null && !user.imageSrc.isEmpty() ? user.imageSrc : "images/user-no-image.jpg")}" alt="alt"/>
                                            </div>
                                            <a href="javascript:void(0)" class="change-img d-flex align-items-center gap-1">
                                                <span>Thay đổi</span>
                                                <i class='bx bx-edit'></i>
                                            </a>
                                        </div>

                                        <div class="input-box d-flex flex-column">
                                            <label for="st-ac-name">Họ tên</label>
                                            <input type="text" id="st-ac-name" autocomplete="off" value="${user.fullName}" data-name="${user.fullName}" required>
                                        </div>

                                        <div class="setting-email d-flex align-items-center gap-3">
                                            <div class="input-box d-flex flex-column">
                                                <label for="st-ac-email">Email</label>
                                                <input type="email" id="st-ac-email" autocomplete="off" value="${user.email}" data-email="${user.email}" required>
                                            </div>
                                            
                                            <c:if test="${!user.emailVerified}">
                                                <a href="javascript:void(0)" class="confirm-email mt-4 text-success text-decoration-none fw-bold">Xác nhận email</a>
                                            </c:if>
                                        </div>
                                    
                                        <div class="setting-account-btns d-flex gap-2">
                                            <button class="s-btn st-btn-changepass bg-primary">
                                                <i class='bx bx-key me-1 text-white'></i>
                                                <span class="text-white">Đổi mật khẩu</span>
                                            </button>
                                            <button class="s-btn bg-info" onclick="window.location.href='./logout'">
                                                <i class='bx bx-log-out me-1 text-white'></i>
                                                <span class="text-white">Đăng xuất</span>
                                            </button>
                                            <button class="s-btn bg-success st-btn-changeinfo" disabled>
                                                <i class='bx bx-save me-1 text-white'></i>
                                                <span class="text-white">Lưu</span>
                                            </button>
                                        </div>
                                    
                                        <a href="javascript:void(0)" class="delete-account mt-3 text-danger text-decoration-none fw-bold">Xóa tài khoản</a>
                                    </div>
                                    
                                    <div class="setting-content-item d-flex flex-column gap-4" data-setting="languages">
                                        <div>
                                            <div class="lang-select">
                                                <div class="lang-select-btn d-flex align-items-center justify-content-between">
                                                    <span>${(sessionScope.lang == 'en') ? "English" : "Tiếng Việt"}</span>
                                                    <i class='bx bx-chevron-down'></i>
                                                </div>
                                                <div class="lang-select-content">
                                                    <div class="lang-item" data-lang="vi">Tiếng Việt</div>
                                                    <div class="lang-item" data-lang="en">English</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="setting-content-item d-flex flex-column gap-4" data-setting="other">
                                        <div>
                                            
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                
            </main>
                
            <section class="upload-image-wrapper form-container">
                <section class="upload-image form-box">
                    <div class="form-box-header">
                        <h4>Upload hình ảnh</h4>
                    </div>
                    <form class="form-main" action="#" method="POST" enctype="multipart/form-data">
                        <div class="image-uploaded">
                            <div class="click-upload-image w-100 h-100 d-flex flex-column align-items-center justify-content-center">
                                <i class='bx bx-cloud-upload'></i>
                                <p class="m-0 p-0">Tải lên hoặc kéo hình ảnh vào đây.</p>
                            </div>
                        </div>
                        <input id="upload-image" type="file" accept="image/*" hidden />
                        <input id="cropped-image" name="file" type="file" hidden />
                        <div class="form-error mt-3 d-none"></div>
                        <div class="form-action d-flex gap-3 align-items-center justify-content-end">
                            <button type="button" class="s-btn close-form-btn">
                                Hủy
                            </button>
                            <button type="submit" class="s-btn submit-form-btn">
                                Cập nhật
                            </button>
                        </div>
                    </form>
                </section>
            </section>

            <section class="crop-image-wrapper">
                <section class="crop-image">
                    <div class="preview-image-box">
                        <img id="preview-image" src="" alt=""/>
                    </div>

                    <div class="d-flex gap-3 align-items-center justify-content-end mt-4">
                        <button class="s-btn close-crop-image">
                            Hủy
                        </button>
                        <button class="s-btn submit-crop-image">
                            Xác nhận
                        </button>
                    </div>
                </section>
            </section>
                                                    
            <section class="change-password-wrapper form-container">
                <section class="change-password form-box">
                    <div class="form-box-header">
                        <h4>Đổi mật khẩu</h4>
                    </div>
                    <form class="form-main" action="#" method="POST">
                        <div class="form-error mt-3 d-none"></div>
                        <div>
                            <div class="input-box d-flex flex-column">
                                <label for="oldPassword">Mật khẩu cũ</label>
                                <input type="password" name="oldPassword" id="oldPassword" autocomplete="off" required />
                            </div>
                            <div class="input-box d-flex flex-column mt-3">
                                <label for="newPassword">Mật khẩu mới</label>
                                <input type="password" name="newPassword" id="newPassword" autocomplete="off" required />
                            </div>
                                 <div class="input-box d-flex flex-column mt-3">
                                <label for="newPasswordConfirm">Xác nhận mật khẩu mới</label>
                                <input type="password" name="newPasswordConfirm" id="newPasswordConfirm" autocomplete="off" required />
                            </div>
                        </div>

                        <div class="form-action d-flex gap-3 align-items-center">
                            <button type="submit" class="s-btn submit-form-btn">
                               Thay đổi
                            </button>
                            <button type="button" class="s-btn close-form-btn">
                                <fmt:message bundle="${bundle}" key="form_close_btn" />
                            </button>
                        </div>

                    </form>
                </section>
            </section>
            
            <jsp:include page="layout/task_info.jsp" />
            <jsp:include page="layout/action_form.jsp" />      
            <jsp:include page="layout/mb_navigation.jsp" />
            <jsp:include page="layout/confirm_box.jsp" />
 
            <button class="add-task-floating">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                    <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
                </svg>
            </button>
                
            <section class="overlay"></section>
        </section>
    </body>
</html>
