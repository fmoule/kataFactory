import axios, {AxiosRequestConfig, AxiosResponse} from "axios";

///// Fonctions utilitaires :

export function callGET<T>(url: string): Promise<AxiosResponse<T>> {
    return axios.get(url);
}

export function callPOST<U, V>(url: string, body?: U): Promise<AxiosResponse<V>> {
    let config: AxiosRequestConfig = {
        headers: {
            "Content-Type": "application/json"
        }
    }
    return axios.post(url, body, config);
}

export function callPUT<T>(url: string): Promise<AxiosResponse<T>> {
    return axios.put(url);
}