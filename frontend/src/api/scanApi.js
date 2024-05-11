import axiosConfig from "./axiosConfig";


export const scanApi = {
    async getScans() {
        try {
            const response = await axiosConfig.get("/scans");
            console.log("Scans: ", response.data);
            return response.data;
        } catch (error) {
            console.error(error);
        }
    },

    async postScanTheharvester(domain, datasource) {
        try {
            await axiosConfig.post("/scans/theharvester", { domain, datasource });
        } catch (error) {
            console.error(error);
        }
    },

    async postScanAmass(domain) {
        try {
            await axiosConfig.post("/scans/amass", { domain });
        } catch (error) {
            console.error(error);
        }
    }
}