import React from 'react';
import { createRoot } from 'react-dom/client';
import UserDashboard from './UserDashboard.jsx';
import './UserDashboard.css';

createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <UserDashboard />
    </React.StrictMode>,
); 