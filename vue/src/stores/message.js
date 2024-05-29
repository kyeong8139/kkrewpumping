import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const localhost = `localhost:8080`
const AWS_IP = `3.106.220.75:8080`;
const REST_MESSAGE_API = `http://${AWS_IP}/message`; 

export const useMessageStore = defineStore('message', () => {
    
    const messageList = ref([])

    const getMessage = function(userId) {
        axios.get(`${REST_MESSAGE_API}/${userId}`)
        .then((response) => {
            messageList.value = response.data
        })
    }

    return {
        messageList,
        getMessage
    }
})