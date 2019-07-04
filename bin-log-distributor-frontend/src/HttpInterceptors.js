import axios from 'axios'
import Vue from 'vue'

// 超时时间
axios.defaults.timeout = 60000;
axios.interceptors.response.use(
    data => {
        return data
    },
    error => {
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    sessionStorage.removeItem('user');
                    new Vue().$message({message: '没有权限',type: 'warning',onClose:function () {
                        window.location.reload();
                    }});
                    break;
                case 403:
                   sessionStorage.removeItem('user');
                    new Vue().$message({message: '没有权限',type: 'warning',onClose:function () {
                        window.location.reload();
                    }});
                    break;
                case 500:
                    new Vue().$message({message: '出现异常',type: 'error'});
                    break;
            }
        } else {
       //     new Vue().$message({message: '请求超时，请检查网络',type: 'warning'});
        }
        return Promise.reject(error)
    }
);

export default axios
