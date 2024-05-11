import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import { scanStore } from "../../store/ScanStore";

const ScanForm = () => {

    const [textInputValue, setTextInputValue] = useState("");
    const [selectedOption, setSelectedOption] = useState("");
    const [secondDropdownValue, setSecondDropdownValue] = useState("");
    const handleTextInputChange = (e) => setTextInputValue(e.target.value);
    const handleOptionChange = (e) => setSelectedOption(e.target.value);
    const handleSecondDropdownChange = (e) => setSecondDropdownValue(e.target.value);

    const handleSendRequest = () => {
        scanStore.startScan(selectedOption, textInputValue, secondDropdownValue);
    };

    const isInputValid = (textInputValue.trim() !== "") &&
        ((selectedOption === "amass") ||
            (selectedOption === "theharvester" && secondDropdownValue !== ""));


    return (
        <div className="mb-3 d-flex flex-column align-items-between" style={{ backgroundColor: "white", padding: "1rem" }}>
            <Row className="mb-3">
                <Col md={7}>
                    <Form.Group controlId="textInput">
                        <Form.Label>Domain</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="example.com"
                            value={textInputValue}
                            onChange={handleTextInputChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={5}>
                    <Form.Group controlId="dropdown">
                        <Form.Label>Tool</Form.Label>
                        <Form.Control
                            as="select"
                            value={selectedOption}
                            onChange={handleOptionChange}
                        >
                            <option value="">Tools</option>
                            <option value="amass">Amass</option>
                            <option value="theharvester">The Harvester</option>
                        </Form.Control>
                    </Form.Group>
                </Col>
            </Row>
            {selectedOption === "theharvester" && (
                <Row className="mb-3">
                    <Col md={7}>
                        <Form.Group controlId="secondDropdown">
                            <Form.Label>Data Source</Form.Label>
                            <Form.Control
                                as="select"
                                value={secondDropdownValue}
                                onChange={handleSecondDropdownChange}
                            >
                                <option value="">Data sources</option>
                                <option value="baidu">baidu</option>
                                <option value="bing">bing</option>
                                <option value="duckduckgo">duckduckgo</option>
                                <option value="google">google</option>
                                <option value="linkedin">linkedin</option>
                                <option value="threatcrowd">threatcrowd</option>
                                <option value="twitter">twitter</option>
                                <option value="vhost">vhost</option>
                                <option value="yahoo">yahoo</option>
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>
            )}
            <Row>
                <Col >
                    <Button variant="primary" onClick={handleSendRequest} disabled={!isInputValid}>Scan</Button>
                </Col>
            </Row>
        </div>
    );
};

export default ScanForm;