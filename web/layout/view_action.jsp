<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="sort-filter-box d-flex align-items-center gap-2">
    <div class="position-relative">
        <button class="sort-action s-btn">
            <i class='bx bx-sort-alt-2'></i>
        </button>

        <div class="view-action-box">
            <div class="sort-selection-box">
                <div class="sort-selection">
                    <input type="radio" name="sort-by" id="sort-asc" hidden>
                    <label class="d-flex align-items-center gap-2" for="sort-asc">
                        <i class='bx bx-up-arrow-alt'></i>
                        <span>Tăng dần theo thời gian</span>
                    </label>
                </div>
                <div class="sort-selection">
                    <input type="radio" name="sort-by" id="sort-desc" hidden>
                    <label class="d-flex align-items-center gap-2" for="sort-desc">
                        <i class='bx bx-down-arrow-alt'></i>
                        <span>Giảm dần theo thời gian</span>
                    </label>
                </div>
            </div>
        </div>
    </div>

    <div class="position-relative">
        <button class="filter-action s-btn">
            <i class='bx bx-filter' ></i>
        </button>

        <div class="view-action-box">
            Filter box
        </div>
    </div>

    <div class="position-relative">
        <button class="view-action s-btn">
            <i class='bx bxs-grid-alt'></i>
            <i class='bx bx-list-ul d-none'></i>
        </button>

        <div class="view-action-box">
            <div class="view-selection-box d-flex">
                <div class="view-selection">
                    <input type="radio" name="view-type" id="view-list" hidden>
                    <label for="view-list" class="d-flex align-items-center justify-content-center">
                        <i class='bx bx-list-ul'></i>
                    </label>
                </div>
                <div class="view-selection">
                    <input type="radio" name="view-type" id="view-grid" hidden>
                    <label for="view-grid" class="d-flex align-items-center justify-content-center">
                        <i class='bx bxs-grid-alt' ></i>
                    </label>
                </div>
            </div>
        </div>
    </div>
</div>