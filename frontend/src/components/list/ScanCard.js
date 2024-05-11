import Button from "react-bootstrap/Button";
import React from "react";
import Card from "react-bootstrap/Card";

const ScanCard = ({ scan, handleShowModal }) => {
    const getStatusColor = () => {
        return scan.inProgress ? "orange" : "green";
    };

    return (
        <Card style={{ color: "#000", marginBottom: "16px", position: "relative" }}>
            <Card.Body>
                <Card.Title style={{ fontWeight: "bold", fontSize: "1.6rem" }}>
                    {scan.type === "theharvester" ? "The Harvester" : "Amass"}
                    <span style={{ position: "absolute", top: "1.5rem", right: "2rem", fontSize: "0.8rem" }}>{scan.inProgress ? " inProgress" : " done"}</span>
                    <span style={{ position: "absolute", top: "1.5rem", right: "1rem", color: getStatusColor(), fontSize: "0.8rem" }}>•</span>
                </Card.Title>
                <Card.Text style={{ fontWeight: 500, fontSize: "1.1rem" }}>Domain: {scan.domain}</Card.Text>
                <Card.Text style={{ fontWeight: 500, fontSize: "1.1rem" }}>Created at: {scan.begin}</Card.Text>
                <Card.Text style={{ fontWeight: 500, fontSize: "1.1rem" }}>Ended at: {scan.end}</Card.Text>
                {scan.inProgress ? (
                    <Card.Text>Scanning for subdomains...</Card.Text>
                ) : (
                    <Card.Text>Number of subdomains: {scan.numberOfSubdomains}</Card.Text>
                )}
                {scan.inProgress ? (
                    <Card.Text>Scanning for emails...</Card.Text>
                ) : (
                    <Card.Text>Number of emails: {scan.numberOfEmails}</Card.Text>
                )}
                <Button variant="secondary" onClick={() => handleShowModal(scan)}>Details</Button>
            </Card.Body>
        </Card>
    );
};

export default ScanCard;