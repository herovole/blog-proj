import React from "react";
import {Link, NavLink} from "react-router-dom"; // 追加

export const AdminHeader = () => {
    return (
        <div className="flex-container">
            <span>
                <Link to="/admin">admin</Link>
                <NavLink style={({active}) => (active ? {color: 'red'} : undefined)} to="/admin">admin</NavLink>
            </span>
            <span>
                <Link to="/admin/articles">articles</Link>
                <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                         to="/admin/articles">articles</NavLink>
            </span>
            <span>
                <Link to="/admin/newarticle">admin-newarticle</Link>
                <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                         to="/admin/newarticle">admin-NewArticle</NavLink>
            </span>
            <span>
                <Link to="/admin/topictags">topicTags</Link>
                <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                         to="/admin/topictags">topicTags</NavLink>
            </span>
            <span>
                <Link to="/admin/sandbox">sandbox</Link>
                <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                         to="/admin/sandbox">sandbox</NavLink>
            </span>
        </div>
    )
}