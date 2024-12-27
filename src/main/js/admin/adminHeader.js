import React from "react";
import {Link, NavLink} from "react-router-dom"; // 追加

export const AdminHeader = () => {
    return (
        <div>
            <div className="flex-container">
                <span>
                    <Link to="/admin">admin</Link>
                    <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                             to="/admin">admin</NavLink>
                </span>
                <span>
                    <Link to="/admin/articles">articles</Link>
                    <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                             to="/admin/articles">articles</NavLink>
                </span>
                <span>
                    <Link to="/admin/articles/new">newarticle</Link>
                    <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                             to="/admin/newarticle">NewArticle</NavLink>
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
            <div className="flex-container">
                <span>
                    <Link to="/articles">articles</Link>
                    <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                             to="/articles">articles</NavLink>
                </span>
                <span>
                    <Link to="/articles/1">article_view 0</Link>
                    <NavLink style={({active}) => (active ? {color: 'red'} : undefined)}
                             to="/articles/1">article_view 0</NavLink>
                </span>
            </div>
        </div>
    )
}