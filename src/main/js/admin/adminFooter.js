import React from "react";
import { Link, NavLink } from "react-router-dom"; // 追加

export const AdminFooter = () => {
    return (
        <ul>
            <li>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/admin/adminEntrance">admin entrance</NavLink>
            </li>
            <li>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/admin/newarticle">admin newarticle</NavLink>
            </li>
            <li>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/admin/listarticles">admin listarticles</NavLink>
            </li>
        </ul>
    )
}