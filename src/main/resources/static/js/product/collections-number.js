//데이터 들고오기

class CollectionsApi {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null){
            this.#instance = new CollectionsApi();
        }
        return this.#instance;
    }

    getCollections(page) {
        let responseData = null;

        const url = location.href; //주소들고오기
        const category = url.substring(url.lastIndexOf("/") + 1); //맨뒤에서 첫번째 /부터 잘라내기

        $.ajax({
            async: false,
            type: "get",
            url: "/api/collections/" + category,
            data: {
                "page": page 
            },
            dataType: "json",
            success: (response) => {
                responseData = response.data;
            },
            error: (error) => {
                console.log(error);
            }
        });

        return responseData;
    }

}

class PageNumber{
    #page = 0;
    #maxPageNumber = 0;
    #pageNumberList = null;

    constructor(page, totalCount) {
        this.#page = page;
        this.#maxPageNumber = totalCount % 16 == 0 ? Math.floor(totalCount / 16) : Math.floor(totalCount / 16) + 1;
        //Math.floor > 소수점 버림
        this.#pageNumberList = document.querySelector(".page-number-list");
        this.#pageNumberList.innerHTML = "";
        this.loadPageNumbers();
    }

    loadPageNumbers() {
        this.createPreButton();
        this.createNumberButtons();
        this.createNextButton();
    }

    createPreButton(){
        if(this.#page != 1){
            const pageNumberList = document.querySelector(".page-number-list");
            this.#pageNumberList.innerHTML += `
                    <a href="javascript:void(0)"><li>&#60;</li></a>
            `;
        }

    }

    createNumberButtons(){
        const startIndex = this.#page % 5 == 0 ? this.#page - 4 : this.#page - (this.#page % 5) + 1; //시작인덱스 정하는 공식 (1,6,11,16)
        const endIndex = startIndex + 4 <= this.#maxPageNumber ? startIndex + 4 : this.#maxPageNumber;

        for(let i = startIndex; i <= endIndex ; i++){
            this.#pageNumberList.innerHTML += `
                <a href="javascript:void(0)"><li>${i}</li></a>
            `;
        }
    }

    createNextButton(){
        if(this.#page != this.#maxPageNumber){
            this.#pageNumberList.innerHTML += `
                    <a href="javascript:void(0)"><li>&#62;</li></a>
            `;
        }
    }
}

class CollectionsService{
    static #instance = null;

    static getInstance() {
        if(this.#instance == null){
            this.#instance = new CollectionsService();
        }
        return this.#instance;
    }

    collectionsEntity = {
        page: 1,
        totalCount: 0
    }

    loadCollections() {
        const responseData = CollectionsApi.getInstance().getCollections(this.collectionsEntity.page);
        this.collectionsEntity.totalCount = responseData[0].productTotalCount;
        new PageNumber(this.collectionsEntity.page, this.collectionsEntity.totalCount);
        
    }


}

window.onload = () => {
    CollectionsService.getInstance().loadCollections();
}