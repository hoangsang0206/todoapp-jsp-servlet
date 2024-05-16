<%-- 
    Document   : verifysuccess
    Created on : May 15, 2024, 10:30:01 PM
    Author     : Sang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
        <title>Xác nhận email</title>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Source+Sans+3:ital,wght@0,200..900;1,200..900&display=swap');
            
            * {
                color: #fff;
                font-family: "Source Sans 3", sans-serif;;
            }
            
            main {
                background: #171717;
                display: grid;
                place-items: center;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
            }
            
            main > div {
                display: flex;
                align-items: center;
                flex-direction: column;
            }
            
            i {
                color: #34a853;
                font-size: 40px;
            }
            
            h2 {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <main>
            <div>
                <i class='bx bx-check-shield'></i>
                <h2>Xác nhận địa chỉ email thành công</h2>
            </div>
        </main>
    </body>
</html>
