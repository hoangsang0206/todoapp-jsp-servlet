declare const $: any;

const setMainWidth = (): void => {
    if(window.innerWidth >= 1024) {
        let main: any = $('main');
        let taskInf: any = $('.task-infomation-wrapper');
        let nav: any = $('.sidebar');

        if(!taskInf.hasClass('close') && !nav.hasClass('close')) {  
            main.css('width', 'calc(100% - 570px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(3, 1fr)')

        } else if(!nav.hasClass('close') && taskInf.hasClass('close')) {
            main.css('width', 'calc(100% - 270px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(4, 1fr)')
        } else if(nav.hasClass('close') && !taskInf.hasClass('close')) {
            main.css('width', 'calc(100% - 370px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(4, 1fr)')
        } else {
            main.css('width', 'calc(100% - 70px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(5, 1fr)')
        }
    }
}

const showOverlay = (): void => {
    $('.overlay').show();
}

const hideOverlay = (): void => {
    $('.overlay').hide();
}

const showContentLoading = (content_box: any): void => {
    const loader_element: string = `<div class="loader-box"><span class="loader"></span></div>`;
    content_box.css('position', 'relative');
    content_box.append(loader_element);
}

const hideContentLoading = (content_box: any): void => {
    setTimeout(() => {
        content_box.find('.loader-box').remove();
    }, 1000);
}

const closeTaskList = (taskList: any): number => {
    let height: number = taskList.height();
    let targetHeight: number = 0;
    let duration: number = 300; //0.3s

    taskList.data('box-height', height);

    taskList.animate({
        height: targetHeight
    }, duration)

    return height;
}

const showTaskList = (taskList: any, height: number): void => {
    let duration: number = 300; //0.3s

    taskList.animate({
        height: height
    }, duration)
}

const showActionForm = (form_wrapper: any): void => {
    form_wrapper.addClass('show');
    form_wrapper.find('.form-box').addClass('show');

    $('input[type="datetime-local"]').val(() => {
        return new Date(new Date().getTime() + 60 * 60000).toLocaleString('sv', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });
    });
}

const hideActionForm = (form_wrapper: any): void => {
    form_wrapper.removeClass('show');
    form_wrapper.find('.form-box').removeClass('show');
    form_wrapper.find('.custom-select').removeClass('open');
}

//Custom select ------

const loadSelectData = (): Promise<any[]> => {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'api/categories',
            type: 'GET',
            success: (response) => {    
                if(response != null) {
                    resolve(response);
                } else {
                    reject(new Error("Empty response"));
                }
            },
            error: (error) => {
                console.error(error);
                reject(error);
            }
        })
    })
}

const loadSelectElements = (data: any[]): void => {
    $('.select-options-box').empty();
    data.map(item => {
        let element = `<div class="select-option" data-value="${item?.id}" data-name="${item?.cateName}" data-color="${item?.iconColor}">
                        <span class="nav-color-icon" style="background: ${item?.iconColor != null ? item.iconColor : "#e30019"};"></span>
                        <span class="select opt-name">${item?.cateName}</span>
                    </div>`;

        $('.select-options-box').append(element);
    })  
}

$(document).ready(() => {
    loadSelectData()
        .then((data) => {
            loadSelectElements(data)
        })
        .catch((error) => { })
})

const getSelectedElements = () => {
    return $('.selected-option').toArray();
}

const getSelectedValue = (): string[] => {
    const selectedElements = getSelectedElements();
    let selectedValues: string[] = [];
    selectedElements.map((item) => {
        selectedValues.push($(item).data('value'))
    })
    
    return selectedValues;
}

const checkExistItemSelected = (value: string): boolean => {
    for(let element of getSelectedElements()) {
        if($(element).data('value') === value) {
            return true;
        }
    }

    return false;
}

$('.show-select-options i').click(function() {
    $(this).closest('.custom-select').toggleClass('open');
})

$(document).click(function(event) {
    if(!$(event.target).closest('.custom-select').length) {
        $('.custom-select').removeClass('open');
    }
})

$(document).on('click', '.select-option', function() {
    let value: string = $(this).data('value');

    if(checkExistItemSelected(value)) {
        return;
    }

    let itemName: string = $(this).data('name');
    let colorData: string = $(this).data('color');
    let selectedEl: string = `<div class="selected-option" data-value="${value}" data-name="${itemName}" data-color="${colorData}">
                                <div class="selected-opt-content">
                                    <span class="nav-color-icon" style="background: ${colorData != 'undefined' && colorData.length > 0 ? colorData : "#e30019"};"></span>
                                    <span class="selected-opt-name">${itemName}</span>
                                </div>
                                <div class="delete-selected-opt">
                                    <i class='bx bx-x'></i>
                                </div>
                            </div>`;

    $('.selected-box').append(selectedEl);
})

$(document).on('click', '.delete-selected-opt', function() {
    let parentEl: any = $(this).closest('.selected-option');
    parentEl.remove();
})

$('#search-option').keyup(function() {
    const searchText = $(this).val();
 
    loadSelectData()
        .then((data) => {
            if(searchText.length > 0) {
                let filteredCategories = data.filter(category => category?.cateName.toLowerCase().includes(searchText.toLowerCase()));
                loadSelectElements(filteredCategories);
            } else {
                loadSelectElements(data);
            }
        })
        .catch((eror) => {
            
        })
})



//------------------------------------------

$(window).on('load', () => {
    $('.page-loader').removeClass('show');
})

$(window).resize(() => {
    if(window.innerWidth < 768) {
        $('.sidebar').removeClass('close');
    }
})

$('.toggle-nav').click(() => {
    if(window.innerWidth < 768) {
        $('.sidebar').removeClass('show');
        hideOverlay();
    } else {
        $('.sidebar').toggleClass('close');
        setMainWidth();
    }
})

$('.mb-toggle-nav').click(function () { 
    $('.sidebar').removeClass('show');
    hideOverlay();
});

$('.close-task-info').click(() => {
    $('.task-infomation-wrapper').addClass('close');
    setMainWidth();
    hideOverlay();
})

$('.overlay').click(() => {
    $('.sidebar').removeClass('show');
    $('.task-infomation-wrapper').addClass('close');
    hideActionForm($('.form-full-wrapper'));
    hideOverlay();
})

//Click to show navigation (mobile)
$('.show-nav-action').click(() => {
    $('.sidebar').addClass('show');
    showOverlay();
})


$('.toggle-task-list').click(function() {
    $(this).toggleClass('active');

    let taskList: any = $(this).parent().next('.task-list');

    if(!taskList.hasClass('close')) {
        taskList.addClass('close');
        closeTaskList(taskList);
    } else {
        taskList.removeClass('close');
        
        let height: number = taskList.data('box-height');

        showTaskList(taskList, height);
    }
})

$('.edit-sub-task-btn').click(function() {
    $(this).parent().find('.edit-sub-task').addClass('show');
    $(this).parent().find('.edit-sub-task input').focus();
    $(this).parent().find('.edit-sub-task input').val($(this).parent().find('.sub-task-label span').text())
})

$('.edit-sub-task-submit').click(function() {
    $(this).closest('.edit-sub-task').removeClass('show');
})

$('.add-new-task, .add-task-floating, .welcome-box button').click(() => {
    showActionForm($('.create-task-wrapper'));
    showOverlay();
})

$('.close-form-btn').click(function() {
    hideActionForm($(this).closest('.form-container'));
    hideOverlay();
})

$('.add-cate-header, .add-cate-action').click(() => {
    showActionForm($('.create-category-wrapper'));
    showOverlay();
})

$('.add-note-btn').click(() => {
    
    showOverlay();
})


$('.header-notifications').click(function() {
    $(this).find('.header-icon').toggleClass('active');
    $('.notifications-wrapper').toggleClass('show');
})

//
const setLightTheme = (): void => {
    $('body').css('--current-bg', 'var(--bg-light)');
    $('body').css('--current-content-bg', 'var(--bg-light-content)');
    $('body').css('--current-text-color', 'var(--text-color-black)');
    $('body').css('--current-task-background', 'var(--task-background-light)');
    $('body').css('--nav-text', 'var(--text-color-gray)');
    $('body').css('--border-input', '#ddd');
    
    $('.theme-toggle').css('background', '#ebebeb');
    $('.search-form').css('background', '#fff');
    $('.logo-text').css('color', 'var(--text-color-gray)');
    $('.sort-action, .filter-action, .view-action').css('background', '#e7e7e7');
}

const setDarkTheme = (): void => {
    $('body').css('--current-bg', 'var(--bg-dark)');
    $('body').css('--current-content-bg', 'var(--bg-dark-content)');
    $('body').css('--current-text-color', 'var(--text-color-white)');
    $('body').css('--current-task-background', 'var(--task-background-dark)');
    $('body').css('--nav-text', 'var(--text-color-white)');
    $('body').css('--border-input', 'var(--border-input-dark)');

    $('.theme-toggle ').css('background', '#242424');
    $('.search-form').css('background', '#3a3b3c');
    $('.search-form').css('border', 'none');
    $('.logo-text').css('color', '#fff');
    $('.sort-action, .filter-action, .view-action').css('background', 'var(--bg-dark-content)');
}

$(document).ready(function() {
    let radioThemeLight: any = $('#light-theme');
    let radioThemeDark: any = $('#dark-theme');
    let lctTheme: any = localStorage.getItem('theme');

    if(radioThemeLight.prop('checked') || lctTheme == 'light') {
        setLightTheme();

        radioThemeLight.prop('checked', true);
    } else if(radioThemeDark.prop('checked') || lctTheme == 'dark') {
        setDarkTheme();

        radioThemeDark.prop('checked', true);
    }

    radioThemeLight.change(function() {
        if($(this).prop('checked')) {
            setLightTheme();
            localStorage.setItem('theme', 'light');
        }
    })

    radioThemeDark.change(function() {
        if($(this).prop('checked')) {
            setDarkTheme();
            localStorage.setItem('theme', 'dark');
        }
    })
})
//

$('.sort-filter-box button').click(function() {
    $('.view-action-box').not($(this).next('.view-action-box')).removeClass('show');
    $(this).next('.view-action-box').toggleClass('show');
})

$('.form-container').click(function(event) {
    if(!$(event.target).closest('.form-box').length) {
        hideActionForm($(this));
        hideOverlay();
    }
})



//
const appendTaskInfo = (data: any) => {
    $('.task-inf-name').text(data.title);
    $('.task-inf-description').text(data.description);
    $('.task-inf-cate-list').empty();
    $('.sub-task-list').empty();
    
    console.log(data)
              
    data.categories.forEach(category => {
        let iconColor: string = category.iconColor;
        let taskInfItem: any = $(`<div class="task-inf-cate-item d-flex align-items-center"></div>`);
        taskInfItem.append(`<span class="nav-color-icon" style="background: ${typeof iconColor !== 'undefined' ? iconColor : "#e30019"};"></span>`);
        taskInfItem.append(`<span>${category.cateName}</span>`)

        $('.task-inf-cate-list').append(taskInfItem);
    })
     
    data.subTodoList.forEach(subTodo => {
        let str: string = `<div class="sub-task-item d-flex align-items-center">
                        <label for="sub-task-cbox-1" class="sub-task-label d-flex align-items-center gap-2">                         
                            <input type="checkbox" id="sub-task-cbox-1">
                            <span>${subTodo.title}</span>
                        </label>
                        <div class="edit-sub-task-btn">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                            </svg>
                        </div>
                        <div class="edit-sub-task d-flex align-items-center">
                            <form action="#" method="#">
                                <input type="text" name="subtodo_title" required autocomplete="off">
                                <input type="hidden" name="subtdo_id" value="${subTodo.id}" required />
                                <button type="submit" class="edit-sub-task-submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                        <path d="m10 15.586-3.293-3.293-1.414 1.414L10 18.414l9.707-9.707-1.414-1.414z"></path>
                                    </svg>
                                </button>
                            </form>
                            <button class="delete-sub-task" data-id="${subTodo.id}">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                    <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                </svg>
                            </button>
                        </div>
                    </div>`;
                    
        $('.sub-task-list').append(str);
    });
    
    $('.completed-task-btn').data('id', data.id);
    $('.edit-task-btn').data('id', data.id);
    $('.delete-task-btn').data('id', data.id);
}


$(document).on('click', '.task-box, .task-action.info', function() {
    $('.task-box').removeClass('active');
    $(this).addClass('active');
    $('.task-infomation-wrapper').removeClass('close');
    setMainWidth();

    if(window.innerWidth < 768) {
        showOverlay();
    }
    
    showContentLoading($('.task-infomation-wrapper'));
    
    $.ajax({
        url: 'api/todo',
        type: 'GET',
        data: {
            user: 'sang',
            id: $(this).data('id')
        },
        success: (response) => {
            appendTaskInfo(response);
            
            hideContentLoading($('.task-infomation-wrapper'));
        },
        error: () => { }
    })
   
})


$('.completed-task-btn').click(function() {
    console.log($(this).data('id'));
});

$(document).on('click', '.edit-sub-task-btn', function() {
    $(this).parent().find('.edit-sub-task').addClass('show');
    $(this).parent().find('.edit-sub-task input').focus();
    $(this).parent().find('.edit-sub-task input').val($(this).parent().find('.sub-task-label span').text())
})

$('.edit-sub-task-submit').click(function() {
    $(this).closest('.edit-sub-task').removeClass('show');
})


// -----------------------------------------------
//Todo CRUD -------------
//Create todo
$(document).ready(() => {
    $('.create-task form').submit(function(e) {
        e.preventDefault();
        
        const name: string = $(e.target).find('#task-name').val();
        const description: string = $(e.target).find('#task-description').val();
        const date: string = $(e.target).find('#task-time').val();
        const categories = getSelectedValue();
        
        $.ajax({
            url: 'api/todo',
            type: 'POST',
            data: {
               title: name,
               description: description,
               date: date,
               categories: categories.join(',')
            },
            success: (response) => {
                hideActionForm($('.create-task-wrapper'));
                hideOverlay();
                getTodayTodoList();
            },
            error: (error) => {
                console.error('Error when create todo.')
            }
        })
    })
})

//GET today todo list
const getTodayTodoList = () => {
    showContentLoading($('.home-task-list'));
    $.ajax({
        url: 'api/todo?t=day',
        type: 'GET',
        success: (response) => {
            $('.home-task-list').empty();
            
            response.map((item) => {
                let str: string = `<div class="today-task-box d-flex align-items-center justify-content-between gap-2">
                                        <div class="td-task-content d-flex align-items-center gap-3">
                                            <div class="task-status ${item?.is_completed ? 'completed' : 'not-complete'}">
                                                <i class='bx bx-check'></i>
                                            </div>
                                            <div class="td-task-name-des d-flex flex-column gap-1">
                                                <span class="task-name text-nowrap">${item?.title}</span>
                                                <span class="task-des text-nowrap">${item?.description}</span>
                                            </div>
                                        </div>

                                        <div class="d-flex align-items-center gap-4">
                                            <div class="d-flex flex-column align-items-end gap-1">
                                                <span>${item.dateCompleted != 'undefined' ? item.dateCompleted : ""}</span>
                                                <span>${item?.subTodoList.length} Subtasks</span>
                                            </div>

                                            <div class="d-flex align-items-center gap-2">
                                                <div class="task-action info" data-id="${item?.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M11 11h2v6h-2zm0-4h2v2h-2z"></path>
                                                    </svg>
                                                </div>

                                                <div class="task-action edit" data-id="${item?.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                                                    </svg>
                                                </div>

                                                <div class="task-action delete" data-id="${item?.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                                    </svg>
                                                </div>
                                            </div>
                                        </div>
                                    </div>`;
                $('.home-task-list').append(str);
            })
            
            hideContentLoading($('.home-task-list'));
        },
        eror: (error) => {
            console.error("Cannot get todo list.")
        }
    })
}
