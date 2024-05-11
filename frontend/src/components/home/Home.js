import React, { useEffect, useState } from "react";
import { observer } from "mobx-react";
import { scanStore } from "../../store/ScanStore";
import ScanForm from "../form/scanForm";
import ScanList from "../list/ScanList";
import ScanModal from "../modal/scanModal";
import axiosInstance from "../../api/axiosConfig";

const Home = observer(() => {
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        const fetchScans = async () => {
            await scanStore.getScans();
        }
        fetchScans();
    }, []);

    const getScan = async (id) => {
        try {
            const response = await axiosInstance.get(`/scans/${id}`);
            scanStore.setSelectedScan(response.data);
            return response.data;
        } catch (error) {
            console.error(error);
        }
    }

    const handleCloseModal = () => setShowModal(false);
    const handleShowModal = async (scan) => {
        if (scan.inProgress) {
            scanStore.setSelectedScan({ inProgress: true, type: scan.type, domain: scan.domain, begin: scan.begin });
            setShowModal(true);
            return
        }
        scanStore.setSelectedScan(await getScan(scan.id));
        setShowModal(true);
    };



    return (
        <div className="container" style={{ paddingTop: "20px", paddingBottom: "20px" }}>
            <h1 style={{ textAlign: "center", marginBottom: "20px" }}>OSINT Scan</h1>
            <div style={{ marginBottom: "20px" }}>
                <ScanForm
                />
            </div>
            <div>
                {scanStore.scans.length > 0 && <ScanList handleShowModal={handleShowModal} />}
            </div>
            <div>
                <ScanModal showModal={showModal} handleCloseModal={handleCloseModal} />
            </div>
        </div>
    );
})

export default Home;