<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="create-task">
    <div class="create-task-header">
        <h4>Thêm công vi?c m?i</h4>
    </div>
    <div class="create-task-form">
        <form class="d-flex gap-3">
            <div class="form-left">
                <div class="input-box d-flex flex-column">
                    <label for="task-name">Tên công vi?c</label>
                    <input type="text" name="task-name" id="task-name" autocomplete="off" required>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="task-description">Mô t?</label>
                    <textarea name="task-description" id="task-description"></textarea>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="task-categories">Danh m?c</label>
                    <select name="task-categories" id="task-categories" required>
                        <option value="#" selected>H?c t?p</option>
                        <option value="#">Công vi?c</option>
                        <option value="#">Tàu lao</option>
                    </select>
                </div>
                <div class="input-box d-flex flex-column mt-3">
                    <label for="task-time">Th?i gian</label>
                    <input type="date" name="task-time" id="task-time" required>
                </div>
            </div>
            <div class="form-right">

            </div>
        </form>
    </div>
    <div class="create-task-action d-flex gap-3 align-items-center">
        <button class="s-btn create-task-btn">
            Thêm m?i
        </button>
        <button class="s-btn cancle-create-task">
            H?y
        </button>
    </div>
</section>