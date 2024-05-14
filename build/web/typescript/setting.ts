declare const Cropper: any;
let cropper: any;

const showCropper = (file: File) => {
    const reader: FileReader = new FileReader();
    
    reader.onload = function(e) {
        $('#preview-image').removeAttr('src');
        $('#preview-image').attr('src', e.target.result).show();
        $('.crop-image-wrapper').addClass('show');
        
        if(cropper) {
            cropper.replace($('#preview-image')[0].src);
        } else {
            cropper = new Cropper($('#preview-image')[0], {
               aspectRatio: 1,
            });   
        }
    }
    
    reader.readAsDataURL(file);
}

$('.lang-select-btn').click(() => {
    $('.lang-select-content').toggleClass('show');
})

$('.setting-side-item').click(function() {
    $('.setting-side-item').removeClass('active');
    $(this).addClass('active');
    
    let data = $(this).data('setting');
    let elements = $('.setting-content-item').toArray();
    elements.map((element) => {
        if($(element).data('setting') === data) {
            $('.setting-content-item').removeClass('current');
            $(element).addClass('current');
        }
    })
})

$('.change-img').click(function() {
    showActionForm($('.upload-image-wrapper'));
    showOverlay();
})

$(document).on('click', '.image-uploaded', function() {
    $('#upload-image').click();
})

 $('.click-upload-image').on('dragover dragenter', (e) => {
     e.preventDefault();
    $('.click-upload-image').addClass('dragenter');
})

$('.click-upload-image').on('dragleave dragend drop', () => {
    $('.click-upload-image').removeClass('dragenter');
})

$('.click-upload-image').on('drop', (e) => {
    e.preventDefault();
    var files: File[] = e.originalEvent.dataTransfer.files;
    if(files.length > 0) {
        showCropper(files[0]);
    }
})

$('.close-crop-image').click(() => {
    $('.crop-image-wrapper').removeClass('show');
    $('#upload-image').val(null);
})

$('#upload-image').on('change', function(e) {
    const file: File = e.target.files[0];
    showCropper(file);
});

$('.submit-crop-image').click(() => {
    const canvas: any = cropper.getCroppedCanvas();
    if(canvas) {
        canvas.toBlob(function(blob) {
           const croppedImage: File = new File([blob], 'cropped-image.png', {type: 'image/png'});
           const inputFiles: DataTransfer = new DataTransfer();
           inputFiles.items.add(croppedImage);
           
           $('#cropped-image')[0].files = inputFiles.files;
           
           $('.click-upload-image').addClass('uploaded');
           $('.click-upload-image').empty().append(`<img id="image-uploaded" src="" alt=""/>`);
           $('#image-uploaded').attr('src', URL.createObjectURL(croppedImage));
           
        }, 'image/png');
    }
    
    $('.crop-image-wrapper').removeClass('show');
    $('#upload-image').val(null);
})

$('.upload-image form').submit((e) => {
    e.preventDefault();
   
    const file: File = $('#cropped-image')[0].files[0];
    if(file) {
        const formData: FormData = new FormData();
        formData.append('file', file);
        
        const btn_element: string = showButtonLoader($(e.target).find('.submit-form-btn'));
        
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
                console.log(response)
            },
            error: (error) => {
                console.error(error);
            }
        })
    }
});

//
const showOkBox = () => {
}

$('.confirm-email').click(() => {
    $.ajax({
        url: './verify',
        type: 'POST',
        success: () => {
            showOkBox();
            console.log('Email has sent')
        },
        error: () => {
            console.error('Error when send email')
        }
    })
})

//Change language
$('.lang-item').click(function() {
    const value: string = $(this).data('lang');
    
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
    })
})