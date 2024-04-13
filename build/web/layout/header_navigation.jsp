<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="header-navigation d-flex align-items-center justify-content-between position-relative">
    <form action="#" method="#" class="search-form d-flex align-items-center">
        <input type="search" id="search" name="search" placeholder="Tìm kiếm" autocomplete="off" required>
        <div class="vertical-break"></div>
        <button type="submit" class="submit-search">
            <i class="fa-solid fa-magnifying-glass"></i>
        </button>
    </form>


    <div class="d-flex align-items-center justify-content-between gap-3">
        <button class="add-new-task s-btn me-2">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
            </svg>

            <span>Thêm công việc</span>
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
            <a href="javascript:void(0)" class="d-flex align-items-center justify-content-center">
                <i class='bx bx-cog header-icon'></i>
            </a>
        </div>

        <div class="vertical-break"></div>

        <div class="header-account">
            <a href="javascript:void(0)" class="d-flex align-items-center justify-content-center">
                <div class="nav-user-img-box">
                    <img class="nav-user-img" src="images/netbian-anime-2.jpg" alt="">
                </div>
            </a>
        </div>

        <div class="notifications-wrapper">

        </div>

    </div>
</section>