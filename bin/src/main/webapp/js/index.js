// 載入 dom 元素
$(function () {

    var prev_arrow = '<button type="button" class="slick-prev"><</button>';
    var next_arrow = '<button type="button" class="slick-next">></button>';

    // $('.multiple-items').slick({
    //     infinite: false,
    //     slidesToShow: 3,
    //     slidesToScroll: 3,
    //     // adaptiveHeight: true,
    //     arrows: true,
    //     dots: true
    //     // prevArrow: prev_arrow,
    //     // nextArrow: next_arrow,
    //     // respondTo: 'window',
    //     // responsive: 'unslick'
    // });

    $('.responsive').slick({
        dots: true,
        arrows: true,
        infinite: false,
        speed: 300,
        slidesToShow: 3,
        slidesToScroll: 3,
        // adaptiveHeight: true,
        // variableWidth: true,
        responsive: [
            
            {
                breakpoint: 1200,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2
                }
            },
            {
                breakpoint: 576,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
            // You can unslick at a given breakpoint now by adding:
            // settings: "unslick"
            // instead of a settings object
        ]
    });

});