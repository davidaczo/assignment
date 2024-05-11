import React from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import ScanCard from "./ScanCard";
import { observer } from "mobx-react";
import { scanStore } from "../../store/ScanStore";

const ScanList = observer(({ handleShowModal }) => {
    return (
        <div className="mb-3" style={{ marginTop: "34px", paddingLeft: "12px", paddingRight: "12px" }}>
            <h2 style={{ textAlign: "center", marginBottom: "20px" }}>History</h2>
            <Row xs={1} md={3} className="g-4">
                {
                    scanStore.scans.map((scan, index) => (
                        <Col key={index}>
                            <ScanCard scan={scan} handleShowModal={handleShowModal} />
                        </Col>
                    ))
                }
            </Row >
        </div>
    );
});

export default ScanList;