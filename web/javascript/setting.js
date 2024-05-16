let cropper;
const showCropper = (file) => {
    const reader = new FileReader();
    reader.onload = function (e) {
        $('#preview-image').removeAttr('src');
        $('#preview-image').attr('src', e.target.result).show();
        $('.crop-image-wrapper').addClass('show');
        if (cropper) {
            cropper.replace($('#preview-image')[0].src);
        }
        else {
            cropper = new Cropper($('#preview-image')[0], {
                aspectRatio: 1,
            });
        }
    };
    reader.readAsDataURL(file);
};
$('.lang-select-btn').click(() => {
    $('.lang-select-content').toggleClass('show');
});
$('.setting-side-item').click(function () {
    $('.setting-side-item').removeClass('active');
    $(this).addClass('active');
    let data = $(this).data('setting');
    let elements = $('.setting-content-item').toArray();
    elements.map((element) => {
        if ($(element).data('setting') === data) {
            $('.setting-content-item').removeClass('current');
            $(element).addClass('current');
        }
    });
});
$('.change-img').click(function () {
    showActionForm($('.upload-image-wrapper'));
    showOverlay();
});
$(document).on('click', '.image-uploaded', function () {
    $('#upload-image').click();
});
$('.click-upload-image').on('dragover dragenter', (e) => {
    e.preventDefault();
    $('.click-upload-image').addClass('dragenter');
});
$('.click-upload-image').on('dragleave dragend drop', () => {
    $('.click-upload-image').removeClass('dragenter');
});
$('.click-upload-image').on('drop', (e) => {
    e.preventDefault();
    var files = e.originalEvent.dataTransfer.files;
    if (files.length > 0) {
        showCropper(files[0]);
    }
});
$('.close-crop-image').click(() => {
    $('.crop-image-wrapper').removeClass('show');
    $('#upload-image').val(null);
});
$('#upload-image').on('change', function (e) {
    const file = e.target.files[0];
    showCropper(file);
});
$('.submit-crop-image').click(() => {
    const canvas = cropper.getCroppedCanvas();
    if (canvas) {
        canvas.toBlob(function (blob) {
            const croppedImage = new File([blob], 'cropped-image.png', { type: 'image/png' });
            const inputFiles = new DataTransfer();
            inputFiles.items.add(croppedImage);
            $('#cropped-image')[0].files = inputFiles.files;
            $('.click-upload-image').addClass('uploaded');
            $('.click-upload-image').empty().append(`<img id="image-uploaded" src="" alt=""/>`);
            $('#image-uploaded').attr('src', URL.createObjectURL(croppedImage));
        }, 'image/png');
    }
    $('.crop-image-wrapper').removeClass('show');
    $('#upload-image').val(null);
});
$('.upload-image form').submit((e) => {
    e.preventDefault();
    const file = $('#cropped-image')[0].files[0];
    if (file) {
        const formData = new FormData();
        formData.append('file', file);
        const btn_element = showButtonLoader($(e.target).find('.submit-form-btn'));
        $.ajax({
            enctype: 'multipart/form-data',
            url: 'api/azure',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: (response) => {
                $('.st-ac-img img, .nav-user-img').attr('src', response);
                hideButtonLoader($(e.target).find('.submit-form-btn'), btn_element);
                hideActionForm($('.upload-image-wrapper'));
                hideOverlay();
                console.log(response);
            },
            error: (jqXHR, textStatus, errorThrown) => {
                const formErr = $(e.target).find('.form-error');
                formErr.empty();
                formErr.append(`<i class="form-error-icon fa-solid fa-circle-exclamation"></i>
                        <span class="form-error-msg">${jqXHR.responseText}</span>`);
                formErr.removeClass('d-none');
            }
        });
    }
});
$('.confirm-email').click(() => {
    showConfirmBox('Xác nhận địa chỉ Email hiện tại?', '', 'verify-email', '#0d6efd');
    showOverlay();
    $('.confirm-btn').off('click').click(function () {
        const action = $(this).data('confirm');
        if (action === 'verify-email') {
            const btn = $(this);
            const btn_element = showButtonLoader(btn);
            $.ajax({
                url: './verify',
                type: 'POST',
                success: () => {
                    hideConfirmBox();
                    hideButtonLoader(btn, btn_element);
                    showOkBox('Chúng tôi đã gửi một email đến địa chỉ email của bạn');
                },
                error: () => {
                    hideConfirmBox();
                    hideButtonLoader(btn, btn_element);
                    showOkBox(`<div class="d-flex align-items-center gap-2">
                        <i class="form-error-icon fa-solid fa-circle-exclamation"></i>
                        <span>Đã xảy ra lỗi trong quá trình gửi Email</span>
                     </div>`);
                }
            });
        }
    });
});
$('.st-btn-changepass').click(() => {
    showActionForm($('.change-password-wrapper'));
    showOverlay();
});
$('.change-password form').submit((e) => {
    e.preventDefault();
    const button = $(e.target).find('.submit-form-btn');
    const btn_element = showButtonLoader(button);
    const oldPass = $(e.target).find('#oldPassword').val();
    const newPass = $(e.target).find('#newPassword').val();
    const newPassConf = $(e.target).find('#newPasswordConfirm').val();
    $.ajax({
        url: './api/account',
        type: 'POST',
        data: {
            t: 'changepass',
            oldpass: oldPass,
            newpass: newPass,
            newpassconf: newPassConf
        },
        success: (response) => {
            setTimeout(() => {
                hideButtonLoader(button, btn_element);
                hideActionForm($('.change-password-wrapper'));
                clearFormValue($('.change-password-wrapper form'));
                showOkBox('Đổi mật khẩu thành công');
            }, 500);
        },
        error: (jqXHR, textStatus, errorThrown) => {
            setTimeout(() => {
                hideButtonLoader(button, btn_element);
            }, 500);
            const formErr = $(e.target).find('.form-error');
            formErr.empty();
            formErr.append(`<i class="form-error-icon fa-solid fa-circle-exclamation"></i>
                        <span class="form-error-msg">${jqXHR.responseText}</span>`);
            formErr.removeClass('d-none');
        }
    });
});
$('#st-ac-name').keyup(function () {
    if ($(this).val() !== $(this).data('name') || $('#st-ac-email').val() !== $('#st-ac-email').data('email')) {
        $('.st-btn-changeinfo').prop('disabled', false);
    }
    else {
        $('.st-btn-changeinfo').prop('disabled', true);
    }
});
$('#st-ac-email').keyup(function () {
    if ($(this).val() !== $(this).data('email') || $('#st-ac-name').val() !== $('#st-ac-name').data('name')) {
        $('.st-btn-changeinfo').prop('disabled', false);
    }
    else {
        $('.st-btn-changeinfo').prop('disabled', true);
    }
});
$('.st-btn-changeinfo').click(function () {
    const name = $('#st-ac-name').val();
    const email = $('#st-ac-email').val();
    const btn_element = showButtonLoader($(this));
    $.ajax({
        url: './api/account',
        type: 'POST',
        data: {
            t: 'changeinfo',
            name: name,
            email: email
        },
        success: (response) => {
            setTimeout(() => {
                hideButtonLoader($(this), btn_element);
                showOkBox('Đổi thông tin thành công');
                showOverlay();
            }, 500);
        },
        error: () => {
        }
    });
});
$('.lang-item').click(function () {
    const value = $(this).data('lang');
    $.ajax({
        type: 'POST',
        url: `./setting?t=lang&v=${value}`,
        success: () => {
            localStorage.setItem('lang', value);
            window.location.href = '';
        },
        error: (error) => {
            console.error(error);
        }
    });
});
