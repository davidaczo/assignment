import React from "react";
import Modal from "react-bootstrap/Modal";
import { scanStore } from "../../store/ScanStore";
import { observer } from "mobx-react";

const ScanModal = observer(({ showModal, handleCloseModal }) => {
    const selectedScan = scanStore.selectedScan;
    return (
        <Modal show={showModal} onHide={handleCloseModal}>
            <Modal.Header closeButton>
                <Modal.Title>Details</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {selectedScan && (
                    <div>
                        <span>Status: {selectedScan.inProgress ? " inProgress" : " done"}</span>
                        <p>Domain: {selectedScan.domain}</p>
                        <p>Type: {selectedScan.type}</p>
                        <p>Emails:</p>
                        <ul>
                            {selectedScan.emails && selectedScan.emails.map((email, index) => (
                                <li key={index}>{email}</li>
                            ))}
                            {selectedScan.inProgress && <li>Scanning for emails...</li>}
                            {selectedScan.emails && selectedScan.emails.length === 0 && <li>No emails found</li>}
                        </ul>
                        <p>Subdomains:</p>
                        <ul>
                            {selectedScan.subdomains && selectedScan.subdomains.map((subdomain, index) => (
                                <li key={index}>{subdomain}</li>
                            ))}
                            {selectedScan.inProgress && <li>Scanning for subdomains...</li>}

                            {selectedScan.subdomains && selectedScan.subdomains.length === 0 && <li>No subdomains found</li>}
                        </ul>
                    </div>
                )}
            </Modal.Body>
            <Modal.Footer>
                <button className="btn btn-secondary" onClick={handleCloseModal}>Close</button>
            </Modal.Footer>
        </Modal>
    );
});

export default ScanModal;