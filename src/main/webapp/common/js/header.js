//$("header").load("/header.html");

// 載入 dom 元素
$(function () {

//    let top_image_arr = ["../img/hot_event_01.jpg", "../img/hot_event_02.jpg", "../img/hot_event_03.jpg"];
    let top_image_index = 0;
    let log_box_html = `
        <div class="log_box">
            <form action="#">
                <label>e-mail<input type="text" placeholder="請輸入e-mail"></label>
                <label>password<input type="text" placeholder="請輸入密碼"></label>
                <label><input type="checkbox" name="" id="">記住密碼</label>
                <input class="log" type="submit" style="display: none;">
                <input class="reg" type="button" style="display: none;">
                <div class="logbox_submit" id="log_btn"><a href="#">登入</a></div>
                <div class="logbox_submit" id="reg_btn"><a href="#">註冊</a></div>
            </form>
        </div>
        `;
    let fav_html = `<span class="fav"><i class="fas fa-heart fa-lg" style="color: #F55177;"></i></span>`;
    let un_fav_html = `<span class="un_fav"><i class="far fa-heart fa-lg"></i></span>`;
    let read_more_html = `
        `;


    $(".top").removeClass("-scroll");

    // var s = skrollr.init();     //初始化skrollr
    $(window).scroll(function (evt) {
        if ($(window).scrollTop() > 0) {
            $(".top").addClass("-scroll");
        } else {
            $(".top").removeClass("-scroll");
        }
    });

//    // 先暫停所有 a 連結的預設行為
//    $(document).on("click", 'a', function (e) {
//        e.preventDefault();
//    });

    $(".search_icon").on("click", function () {
        $(".search_submit_btn").click();
    });

    $("#logbox_pop").on("click", function (e) {
        e.preventDefault();
        $(this).toggleClass("-hover");
        // console.log($(".log_box"));
        if ($(".log_box").length > 0) {
            $(".log_box").remove();
        } else {
            $(".top").after(log_box_html);
        }
    });

    $(".menu-btn").on("click", function () {
        $(".navigator").slideToggle("-tog");
    });

    // window.addEventListener('resize', function () {
    //     console.log(this.innerWidth);
    //     if (this.innerWidth > 851) {
    //         $(".navigator").removeClass("-tog");
    //     }
    // });

    // $("a#logbox_pop").on("click", function (e) {
    //     e.preventDefault();
    //     $(this).toggleClass("-hover");
    //     // console.log($(".log_box"));
    //     $(".log_box").toggleClass("-none");
    // });

    // $(document).on("click", ".un_fav", function () {
    //     // console.log();
    //     $(this).closest(".un_fav").after(fav_html);
    //     $(this).closest(".un_fav")[0].remove();

    // });

    // $(document).on("click", ".fav", function () {
    //     $(this).closest(".fav").after(un_fav_html);
    //     $(this).closest(".fav")[0].remove();
    // });

    // $(".image").on("mouseover", function () {
    //     $(this).addClass("-on");
    //     $(this).find("img").addClass("-on");
    // });

    // $(".image").on("mouseout", function () {
    //     $(this).removeClass("-on");
    //     $(this).find("img").removeClass("-on");
    // });

    // $(".right").on("click", function () {
    //     let that = $(this).closest(".block").find(".events");
    //     $(this).closest(".block").find(".event").each(function (i, item) {
    //         $(item).fadeToggle("-none");
    //     });
    //     that.animate({ left: -($(this).closest(".block").width()) });
    //     $(this).fadeToggle("-none");
    //     $(this).prev().fadeToggle("-none");

    // });

    // $(".left").on("click", function () {
    //     let that = $(this).closest(".block").find(".events");
    //     $(this).closest(".block").find(".event").each(function (i, item) {
    //         $(item).fadeToggle("-none");
    //     });
    //     that.animate({ left: 0 });
    //     $(this).fadeToggle("-none");
    //     $(this).next().fadeToggle("-none");
    // });

});