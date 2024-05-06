<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="vi_VN" />
<c:if test="${sessionScope.lang == 'en'}">
    <fmt:setLocale value="en_US" />
</c:if>

<fmt:setBundle basename="languages.lang" var="bundle" />

<section class="create-task-wrapper form-container">
    <section class="create-task form-box">
        <div class="form-box-header">
            <h4><fmt:message bundle="${bundle}" key="form_todo_title" /></h4>
        </div>
        <form class="form-main" action="#" method="POST">
            <div>
                
                <div class="input-box d-flex flex-column">
                    <label for="task-name"><fmt:message bundle="${bundle}" key="form_todo_label_name" /></label>
                    <input type="text" name="task-name" id="task-name" autocomplete="off" required>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="task-description"><fmt:message bundle="${bundle}" key="form_todo_label_des" /></label>
                    <input type="text" name="task-description" id="task-description" />
                </div>
                <div class="d-flex flex-column mt-3">
                    <label for="task-categories"><fmt:message bundle="${bundle}" key="form_todo_label_cate" /></label>
                    <div class="custom-select">
                        <div class="select-box">
                            <input type="text" name="select-categories" hidden>
                            <div class="selected-box">

                            </div>
                            <div class="show-select-options">
                                <i class='bx bx-chevron-down'></i>
                            </div>
                        </div>
                        <div class="select-options">
                            <div class="search-option">
                                <input type="search" name="search-option" placeholder="<fmt:message bundle="${bundle}" key="search_placeholder" />">
                            </div>
                            <div class="select-options-box">
                                <div class="select-option" data-value="category1" data-name="Category 1" data-color="#e30019">
                                    <span class="nav-color-icon" style="background: #e30019;"></span>
                                    <span class="select opt-name">Category 1</span>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="task-time"><fmt:message bundle="${bundle}" key="form_todo_label_time" /></label>
                    <input type="datetime-local" name="task-time" id="task-time" required>
                </div>
            </div>

            <div class="form-action d-flex gap-3 align-items-center">
                <button type="submit" class="s-btn submit-form-btn">
                    <fmt:message bundle="${bundle}" key="form_create_btn" />
                </button>
                <button type="button" class="s-btn close-form-btn">
                    <fmt:message bundle="${bundle}" key="form_close_btn" />
                </button>
            </div>

        </form>

    </section>
</section>
                
<section class="edit-task-wrapper form-container">
    <section class="edit-task form-box">
        <div class="form-box-header">
            <h4><fmt:message bundle="${bundle}" key="form_edittodo_title" /></h4>
        </div>
        <form class="form-main" action="#" method="POST">
            <div>
                
                <input type="hidden" name="edit-task-id" id="edit-task-id" />
                <div class="input-box d-flex flex-column">
                    <label for="task-name"><fmt:message bundle="${bundle}" key="form_todo_label_name" /></label>
                    <input type="text" name="edit-task-name" id="edit-task-name" autocomplete="off" required>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="task-description"><fmt:message bundle="${bundle}" key="form_todo_label_des" /></label>
                    <input type="text" name="edit-task-description" id="edit-task-description" />
                </div>
                <div class="d-flex flex-column mt-3">
                    <label for="task-categories"><fmt:message bundle="${bundle}" key="form_todo_label_cate" /></label>
                    <div class="custom-select">
                        <div class="select-box">
                            <input type="text" name="select-categories" hidden>
                            <div class="selected-box">

                            </div>
                            <div class="show-select-options">
                                <i class='bx bx-chevron-down'></i>
                            </div>
                        </div>
                        <div class="select-options">
                            <div class="search-option">
                                <input type="search" name="search-option" placeholder="<fmt:message bundle="${bundle}" key="search_placeholder" />">
                            </div>
                            <div class="select-options-box">
                                <div class="select-option" data-value="category1" data-name="Category 1" data-color="#e30019">
                                    <span class="nav-color-icon" style="background: #e30019;"></span>
                                    <span class="select opt-name">Category 1</span>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="task-time"><fmt:message bundle="${bundle}" key="form_todo_label_time" /></label>
                    <input type="datetime-local" name="edit-task-time" id="edit-task-time" required>
                </div>
            </div>

            <div class="form-action d-flex gap-3 align-items-center">
                <button type="submit" class="s-btn submit-form-btn">
                    <fmt:message bundle="${bundle}" key="form_edit_btn" />
                </button>
                <button type="button" class="s-btn close-form-btn">
                    <fmt:message bundle="${bundle}" key="form_close_btn" />
                </button>
            </div>

        </form>

    </section>
</section>

<section class="create-category-wrapper form-container">
    <section class="create-category form-box">
        <div class="form-box-header">
            <h4><fmt:message bundle="${bundle}" key="form_cate_title" /></h4>
        </div>
        <form class="form-main" action="#" method="POST">
            <div>
                <div class="input-box d-flex flex-column">
                    <label for="cate-name"><fmt:message bundle="${bundle}" key="form_cate_label_name" /></label>
                    <input type="text" name="cate-name" id="cate-name" autocomplete="off" required>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="cate-icon"><fmt:message bundle="${bundle}" key="form_cate_label_color" /></label>
                    <input type="color" value="#e30019" name="cate-icon" id="cate-icon" />
                </div>
            </div>

            <div class="form-action d-flex gap-3 align-items-center">
                <button type="submit" class="s-btn submit-form-btn sm-create-task">
                    <fmt:message bundle="${bundle}" key="form_create_btn" />
                </button>
                <button type="button" class="s-btn close-form-btn">
                    <fmt:message bundle="${bundle}" key="form_close_btn" />
                </button>
            </div>

        </form>
    </section>
</section>

<section class="create-note-wrapper form-container">

</section>
                

<section class="upload-image-wrapper form-container">
    <section class="upload-image form-box">
        <div class="form-box-header">
            <h4>Upload hình ảnh</h4>
        </div>
        <form class="form-main" action="#" method="POST">
            
        </form>
    </section>
</section>