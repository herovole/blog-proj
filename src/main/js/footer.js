import React from "react";
import { Link, NavLink } from "react-router-dom"; // 追加

export const Footer = () => {
    return (
        <ul>
            <li>
                <Link to="/">Home</Link> {/*aタグじゃないよ*/}
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/">Home</NavLink>
            </li>
            <li>
                <Link to="/about">about</Link>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/about">About</NavLink>
            </li>
            <li>
                <Link to="/contact">contact</Link>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/contact">Contact</NavLink>
            </li>
            <li>
                <Link to="/admin">admin</Link>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/admin">admin</NavLink>
            </li>
            <li>
                <Link to="/admin/newarticle">admin-newarticle</Link>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/admin/newarticle">admin-NewArticle</NavLink>
            </li>
            <li>
                <Link to="/admin/sandbox">sandbox</Link>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/admin/sandbox">sandbox</NavLink>
            </li>
            <li>
                <Link to="/admin/articles">articles</Link>
                <NavLink style={({ active }) => (active ? { color: 'red' } : undefined)} to="/admin/articles">articles</NavLink>
            </li>


        </ul>
    )
}