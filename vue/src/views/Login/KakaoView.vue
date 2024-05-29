<template>
    <div class="d-flex justify-content-center">
        <div class="spinner-border" role="status">
            <span class="visually-hidden"></span>
        </div>
    </div>
</template>

<script setup>
import router from '@/router';
import { useUserStore } from '@/stores/user';
import { onMounted, onUnmounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const userStore = useUserStore();

onMounted(() => {
    const code = route.query.code
    userStore.postKakaoUser(code);
})

onUnmounted(() => router.push({ name: "home" }));

</script>

<style scoped>
@keyframes spinner-border {
    to {
        transform: rotate(360deg);
    }
}

.spinner-border {
    width: 3rem;
    height: 3rem;
    display: inline-block;
    border-width: 0.25em;
    border-style: solid;
    border-color: currentColor;
    border-top-color: transparent;
    border-radius: 50%;
    animation: spinner-border 0.75s linear infinite;
}

@keyframes spinner-grow {
    0% {
        transform: scale(0);
    }

    50% {
        opacity: 1;
        transform: none;
    }
}
</style>