import React from "react";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";

const ScanCard = ({ scan, handleShowModal }) => {
    const getStatusColor = () => {
        return scan.inProgress ? "orange" : "green";
    };

    return (
        <Card style={{ marginBottom: "16px", position: "relative", border: "1px solid #ccc", borderRadius: "8px" }}>
            <Card.Body>
                <Card.Title style={{ fontWeight: "bold", fontSize: "1.6rem", position: "relative" }}>
                    {scan.type === "theharvester" ? "The Harvester" : "Amass"}
                    <span style={{ position: "absolute", top: "1.5rem", right: "2rem", fontSize: "0.8rem" }}>{scan.inProgress ? "In Progress" : "Done"}</span>
                    <span style={{ position: "absolute", top: "1.5rem", right: "1rem", color: getStatusColor(), fontSize: "0.8rem" }}>•</span>
                </Card.Title>
                <div style={{ marginTop: "1rem" }}>
                    <div style={{ fontWeight: 500, fontSize: "1.1rem", marginBottom: "0.5rem" }}>Domain: {scan.domain}</div>
                    <div style={{ fontWeight: 500, fontSize: "1.1rem", marginBottom: "0.5rem" }}>Created at: {scan.begin}</div>
                    <div style={{ fontWeight: 500, fontSize: "1.1rem", marginBottom: "1rem" }}>Ended at: {scan.end}</div>
                    {scan.inProgress ? (
                        <div>Scanning for subdomains...</div>
                    ) : (
                        <div>Number of subdomains: {scan.numberOfSubdomains}</div>
                    )}
                    {scan.inProgress ? (
                        <div>Scanning for emails and users...</div>
                    ) : (
                        <div>Number of emails or users: {scan.numberOfEmailsOrUsers}</div>
                    )}
                </div>
                <div style={{ marginTop: "1rem" }}>
                    <Button variant="secondary" onClick={() => handleShowModal(scan)}>Details</Button>
                </div>
            </Card.Body>
        </Card>
    );
};

export default ScanCard;
