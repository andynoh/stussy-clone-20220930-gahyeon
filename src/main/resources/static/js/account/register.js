const registerButton = document.querySelector(".account-button");

registerButton.onclick = () => {
    const accountInputs = document.querySelectorAll(".account-input");

    let user = {
        lastName: accountInputs[0].value,
        firstName: accountInputs[1].value,
        email: accountInputs[2].value,
        password: accountInputs[3].value
    }

    //JSON.stringify() > js 객체를 JSON문자열로 변환
    //JSON.parse() > JSON문자열을 js 객체로 변환

    let ajaxOption = {
        async: false,                       //필수
        type: "post",                       //필수
        url: "/api/account/register",       //필수
        contentType: "application/json",    //전송할 데이터가 json인 경우
        data: JSON.stringify(user),         //전송할 데이터가 있으면
        dataType: "json",                   //json외 text등을 사용할 수 있지만 json 사용
        success: (response, textStatus, request) => {            //성공시 실행 될 메소드
            console.log(response);
            const successURI = request.getResponseHeader("Location");
            location.replace(successURI + "?email=" + response.data);
        },
        error: (error) => {                 //실패시 실행 될 메소드
            console.log(error.responseJSON.data);
            loadErrorMessage(error.responseJSON.data);
        }

    }

    $.ajax(ajaxOption);
}

function loadErrorMessage(errors) {
    const errorList = document.querySelector(".errors");
    const errorMsgs = document.querySelector(".error-msgs");
    const errorArray = Object.values(errors); //errors의 values를 뽑아서 리스트로
    
    errorMsgs.innerHTML = ""; //실행 될 때마다 비우기 clear

    errorArray.forEach(error => {
        errorMsgs.innerHTML += `
            <li>${error}</li>
        `;
    })

    errorList.classList.remove("errors-invisible");
}


