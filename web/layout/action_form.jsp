<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="create-task-wrapper form-container">
    <section class="create-task form-box">
        <div class="form-box-header">
            <h4>Thêm công việc mới</h4>
        </div>
        <form class="form-main" action="#" method="POST">
            <div>
                <div class="input-box d-flex flex-column">
                    <label for="task-name">Tên công việc</label>
                    <input type="text" name="task-name" id="task-name" autocomplete="off" required>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="task-description">Mô tả</label>
                    <input type="text" name="task-description" id="task-description" />
                </div>
                <div class="d-flex flex-column mt-3">
                    <label for="task-categories">Danh mục</label>
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
                                <input type="search" id="search-option" placeholder="Tìm danh mục">
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
                    <label for="task-time">Thời gian hoàn thành</label>
                    <input type="datetime-local" name="task-time" id="task-time" required>
                </div>
            </div>

            <div class="form-action d-flex gap-3 align-items-center">
                <button type="submit" class="s-btn submit-form-btn sm-create-task">
                    Thêm mới
                </button>
                <button type="button" class="s-btn close-form-btn">
                    Hủy
                </button>
            </div>

        </form>

    </section>
</section>

<section class="create-category-wrapper form-container">
    <section class="create-category form-box">
        <div class="form-box-header">
            <h4>Thêm danh mục</h4>
        </div>
        <form class="form-main" action="#" method="POST">
            <div>
                <div class="input-box d-flex flex-column">
                    <label for="cate-name">Tên danh mục</label>
                    <input type="text" name="cate-name" id="cate-name" autocomplete="off" required>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="cate-icon">Màu icon</label>
                    <input type="color" value="#e30019" name="cate-icon" id="cate-icon" />
                </div>
            </div>

            <div class="form-action d-flex gap-3 align-items-center">
                <button type="submit" class="s-btn submit-form-btn sm-create-task">
                    Thêm mới
                </button>
                <button type="button" class="s-btn close-form-btn">
                    Hủy
                </button>
            </div>

        </form>
    </section>
</section>

<section class="create-note-wrapper">

</section>