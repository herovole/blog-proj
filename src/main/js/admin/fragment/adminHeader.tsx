import React from "react";
import {NavLink} from "react-router-dom"; // 追加

export const AdminHeader: React.FC = () => {
    return (
        <div className="header-frame">
            <div className="header-alignment">
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/">【公開】トップ</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/articles">【公開】検索</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/articles/1">【公開】記事0</NavLink>
            </div>
            <div className="header-alignment">
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/admin">【編集】トップ</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/admin/articles">【編集】検索</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/admin/articles/new">【編集】新規</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/admin/topictags">【編集】トピックタグ</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/admin/images">【編集】画像管理</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/admin/users">【編集】ユーザー管理</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/admin/usercomments">【編集】コメント管理</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/admin/sandbox">【編集】サンドボックス</NavLink>
            </div>
        </div>
    );
}