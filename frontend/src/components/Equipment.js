import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {useParams} from 'react-router-dom';
import './Equipment.css';
import Header from './Header';

const Equipment = ({token}) => {
    const {id} = useParams();
    const [equipmentList, setEquipmentList] = useState([]);
    const [selectedEquipment, setSelectedEquipment] = useState({});
    const [timeslots, setTimeslots] = useState([]);
    const [selectedTimeslot, setSelectedTimeslot] = useState(null);
    const [reservationStatus, setReservationStatus] = useState('');
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const checkAuthStatus = () => {
            const token = localStorage.getItem('authToken');
            setIsAuthenticated(!!token);
        };

        checkAuthStatus();
    }, []);

    useEffect(() => {
        axios
            .get(`http://localhost:8080/equipment/company/${id}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            })
            .then((response) => {
                setEquipmentList(response.data);
            })
            .catch((error) => console.error('Error fetching equipment:', error));

        axios
            .get(`http://localhost:8080/equipment/${id}/timeslots`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            })
            .then((response) => {
                setTimeslots(response.data);
            })
            .catch((error) => console.error('Error fetching timeslots:', error));
    }, [id, token]);

    const handleEquipmentChange = (equipmentId, quantity) => {
        setSelectedEquipment((prevState) => ({
            ...prevState, [equipmentId]: parseInt(quantity, 10),
        }));
    };

    const handleReserve = () => {
        if (selectedTimeslot && Object.keys(selectedEquipment).length > 0) {
            const equipmentArray = Object.entries(selectedEquipment).map(([id, amount]) => ({
                id: parseInt(id), amount,
            }));
            console.log(equipmentArray.length)
            console.log(selectedTimeslot)
            axios
                .post('http://localhost:8080/equipment/reserve', {
                    equipment: equipmentArray, timeSlotId: selectedTimeslot
                },
                {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    },
                    params: {
                        companyId: id,
                    }
                })
                .then((response) => {
                    setReservationStatus('Reservation successful');
                    setSelectedEquipment({});
                    window.location.reload();
                })
                .catch((error) => {
                    console.error('Error reserving equipment:', error);
                    setReservationStatus('Reservation failed');
                });
        } else {
            setReservationStatus('Please select equipment and timeslot');
        }
    };

    return (<div>
        <Header isAuthenticated={isAuthenticated}/>
        <div className="reservation-container">
            <h2>Reserve Equipment</h2>

            <h3>Select Equipment and Quantities</h3>
            <ul className="equipment-list">
                {equipmentList.map((eq) => (<li
                    key={eq.id}
                    className={`equipment-item ${selectedEquipment[eq.id] ? 'selected-equipment' : ''}`}
                >
                            <span>
                                {eq.name} (Available: {eq.amount})
                            </span>
                    <input
                        type="number"
                        min="1"
                        max={eq.amount}
                        value={selectedEquipment[eq.id] || 0}
                        onChange={(e) => handleEquipmentChange(eq.id, e.target.value)}
                    />
                </li>))}
            </ul>

            <h3>Available Timeslots</h3>
            <ul className="timeslot-list">
                {timeslots.map((timeslot) => (<li key={timeslot.id} className="timeslot-item">
                    <label>
                        <input
                            type="radio"
                            name="timeslot"
                            value={timeslot.id}
                            checked={selectedTimeslot === timeslot.id}
                            onChange={() => setSelectedTimeslot(timeslot.id)}
                        />
                        {timeslot.startTime} - {timeslot.endTime}
                    </label>
                </li>))}
            </ul>

            <button onClick={handleReserve} className="reserve-button">
                Reserve
            </button>
            {reservationStatus && <p className="reservation-status">{reservationStatus}</p>}
        </div>
    </div>);
};

export default Equipment;
