import { makeObservable, action, observable } from "mobx";
import { scanApi } from "../api/scanApi";

class ScanStore {
    scans = [];
    showModal = false;
    selectedScan = null;

    constructor() {
        makeObservable(this, {
            scans: observable,
            showModal: observable,
            selectedScan: observable,
            getScans: action,
            startScan: action,
            setScans: action,
            pushToScans: action,
            setSelectedScan: action,
        });
    }

    async getScans() {
        try {
            const response = await scanApi.getScans();
            this.setScans(response);
        } catch (error) {
            console.error(error);
        }
    }

    async startScan(tool, domain, datasource = null) {
        try {
            const scanData = {
                type: tool === "theharvester" ? "theharvester" : "amass",
                domain,
                inProgress: true,
                begin: this.formatDate(new Date())
            };

            this.pushToScans(scanData);

            if (tool === "theharvester") {
                await scanApi.postScanTheharvester(domain, datasource);
            } else {
                await scanApi.postScanAmass(domain);
            }

            await this.getScans();
        } catch (error) {
            console.error(error);
        }
    }

    setScans(scans) {
        this.scans = scans;
    }

    pushToScans(scan) {
        this.scans.push(scan);
    }

    setSelectedScan(scan) {
        this.selectedScan = scan;
    }


    formatDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
}

export const scanStore = new ScanStore();
