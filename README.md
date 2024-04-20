#  <div align="center"> ![logo white](https://github.com/jobdfhjfghfg/happymallSpring/assets/155034269/795b48fe-3d54-4991-b15a-7367b6e92a5f) &nbsp; HappyMall
<br></div>

### <div align="center"> [HappyMall 바로가기](http://43.202.12.236:8081)</div>
<br>
<div align="center">testId : admin <br>
                    password : test123 </div>
<br>

<details>
<summary>목차</summary>

1. [프로젝트 소개](#intro)
2. [기술 및 개발 환경](#dex)
3. [개발 일정](#schedule)
4. [백엔드 구현 기능](#feature)
5. [메인 코드](#main)

</details>
<br>

## <span id="intro">1. 프로젝트 소개</span>
<b> HappyMall은 애완동물 쇼핑몰로 애견인들에게 필요한 상품들을 제공하는 홈페이지입니다. </b>

<br>
<br>

## <span id="dex">2. 기술 및 개발 환경</span>

<div align="center">

| FrontEnd | BackEnd | Design | DB | IDE |
| :----: | :----: | :----: | :----: | :----: |
|  <img src="https://img.shields.io/badge/html5-E34F26.svg?style=flat-square&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white">   | <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white"> | <img src="https://img.shields.io/badge/figma-FBCEB1?style=flat-square&logo=figma&logoColor=white"> | <img src="https://img.shields.io/badge/mysql-4479A1?style=flat-square&logo=mysql&logoColor=white"> | <img src="https://img.shields.io/badge/intellijidea-000000?style=flat-square&logo=intellijidea&logoColor=white">  |
</div>

<br>
<br>

## <span id="schedule">3. 개발 일정</span>

<div align="center">
  
| 개발 일정 | 개발 내용 |
| :----: | :----: |
| 1.22 ~ 1.24 <br><hr> 1.25 ~ 1.29 <br><hr> 1.30 ~ 2.8 <br><hr> 3.25 ~ 4.15 <br> | 프로젝트 기획 <br><hr> 와이어 프레임 구상 <br><hr> 메인 페이지 구현 <br><hr> 백엔드 기능 구현 <br>
</div>

## <span id="feature">4. 백엔드 구현 기능</span>

<div align="center">
  
| 기능 목록 | 상세 기능 |
| :----: | :----: |
|  <br> 회원 기능 <br><hr>  <br> 상품 등록 기능 <br><hr> <br>주문 기능<br><hr> 장바구니 기능 <br><hr> 결제 기능 <br><hr> | 1. 회원가입 <br> 2. 로그인 <br><hr> 1. 상품 등록 <br> 2. 상품 재고 확인 <br><hr> 1. 관리자 주문 기능 <br> 2. 회원 주문 기능(미완) <br><hr> 1. 장바구니 담기 <br><hr> 1. 상품 결제 기능(미완) <br><hr>
</div>


## <span id="main">5. 메인 코드 </span>

* 장바구니 추가 addCart 코드

```
if (cart == null) {
            cart = Cart.createCart(member1);
            cartRepository.save(cart);
        }
```
=> cart에 정보가 없으면 새 cart 생성

<br>

```
if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, inventory, count);
            cartInventoryRepository.save(cartItem); }
``` 
=> 장바구니에 정보가 없으면 새 장바구니를 만들어줌

<br>

```
else {
            CartItem updateCartItem = cartItem;
            updateCartItem.setCart(cartItem.getCart());
            updateCartItem.setInventory(cartItem.getInventory());
            updateCartItem.setCount(cart.getCount() + count);
            updateCartItem.setPrice(inventory.getPrice() * (cart.getCount() + count));

            cartInventoryRepository.save(updateCartItem);
        }
```
=> 장바구니에 정보가 있으면 update시켜줌

<br>
<br>
<br>

* 카카오 API 우편번호 검색

```
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
  const btn = document.querySelector("#btn")
  btn.addEventListener("click", () => {
    new daum.Postcode({
      oncomplete: function(data) {
        
        let fullAddr = '';
        let extraAddr = '';

        if (data.userSelectedType === 'R') { //도로명주소인 경우
          fullAddr = data.roadAddress;
        } else {
          fullAddr = data.jibunAddress; //지번주소인 경우
        }

        if (data.userSelectedType === 'R') {

          if (data.bname !== '') {
            extraAddr += data.bname;
          }

          if (data.buildingName !== '') {
            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName); //법정동주소가 비어있지 않으면 빌딩명이랑 붙여서 쓰고 아니면 빌딩명만 쓰기
          }

          fullAddr += (extraAddr !== '' ? '(' + extraAddr + ')' : '');

        } else {
          document.getElementById("extraAddr").value = '';
        }

        document.getElementById('zipcode').value = data.zonecode;
        document.getElementById("fullAddr").value = fullAddr;
        document.getElementById("extraAddr").focus();
      }
    }).open();
  });

</script>
```

==> 카카오 API를 이용한 우편번호 검색

외부 API를 이용한 주소검색으로 우편번호, 주소 등을 간단히 입력가능하게끔 구현함.
