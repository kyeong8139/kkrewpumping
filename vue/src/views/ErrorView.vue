<template>
    <section class="error-page section">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 offset-lg-3 col-12">
                    <div class="error-inner">
                        <h1 @click="goToHome">PAGE NOT FOUND</h1>
                    </div>
                </div>
            </div>
        </div>
    </section>
    </template>
    
    <script setup>
import { useRouter } from 'vue-router';


    const router = useRouter();

    const goToHome = function() {
        router.push({name : "home"})
    }
    </script>
    
    <style scoped>
    .container {
        margin: 100px 0;
    }
    
    .error-page {
        width: 100%;
        text-align: center;
        background: #fff;
        border-top: 1px solid #eee;
        font-size: 16px;
        position: relative;
    }
    
    .error-inner {
        display: inline-block;
    }
    
    .error-inner h1 {
        font-size: 140px;
        text-shadow: 3px 5px 2px #3333;
        color: #999999;
        font-weight: 700;
        transition: color 0.3s, font-size 0.3s; /* 부드러운 변화를 위한 트랜지션 추가 */
    }
    
    .error-inner:hover h1 {
        color: #FF6422;
        font-size: 150px; /* 호버 시 글씨 크기 증가 */
    }
    
    .error-inner h1 span {
        display: block;
        font-size: 25px;
        color: #333;
        font-weight: 600;
        text-shadow: none;
    }
    
    .error-inner p {
        padding: 20px 15px;
    }
    </style>
    