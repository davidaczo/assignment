import React, { useEffect, useState } from "react";
import { observer } from "mobx-react";
import { scanStore } from "../../store/ScanStore";
import ScanForm from "../form/ScanForm";
import ScanList from "../list/ScanList";
import ScanModal from "../modal/ScanModal";
import axiosInstance from "../../api/axiosConfig";

const Home = observer(() => {
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        fetchScans();
    }, []);

    const fetchScans = async () => {
        await scanStore.getScans();
    };

    const getScan = async (id) => {
        try {
            const response = await axiosInstance.get(`/scans/${id}`);
            return response.data;
        } catch (error) {
            console.error("Error fetching scan:", error);
        }
    };

    const handleCloseModal = () => setShowModal(false);

    const handleShowModal = async (scan) => {
        if (scan.inProgress) {
            scanStore.setSelectedScan({
                inProgress: true,
                type: scan.type,
                domain: scan.domain,
                begin: scan.begin
            });
        } else {
            scanStore.setSelectedScan(await getScan(scan.id));
        }
        setShowModal(true);
    };

    return (
        <div className="container" style={{ paddingTop: "20px", paddingBottom: "20px" }}>
            <h1 style={{ textAlign: "center", marginBottom: "20px" }}>OSINT Scan</h1>
            <div style={{ marginBottom: "20px" }}>
                <ScanForm />
            </div>
            <div>
                {scanStore.scans.length > 0 && <ScanList handleShowModal={handleShowModal} />}
            </div>
            <div>
                <ScanModal showModal={showModal} handleCloseModal={handleCloseModal} />
            </div>
        </div>
    );
});

export default Home;
