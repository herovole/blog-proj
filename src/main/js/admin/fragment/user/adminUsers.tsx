import React, {useEffect} from "react";
import {SearchAdminUserOutput} from "../../../service/auth/searchAdminUserOutput";


type AdminUsersType = {
    data: SearchAdminUserOutput;
    reload: () => void;
}


export const AdminUsers: React.FC<AdminUsersType> = ({data, reload}) => {

    useEffect(() => {
    }, []);

    return (
        <table className="admin-table">
            <thead>
            <tr>
                <th className="admin-table-header-cell">ID</th>
                <th className="admin-table-header-cell">Name</th>
                <th className="admin-table-header-cell">Role</th>
                <th className="admin-table-header-cell">Last Login</th>
            </tr>
            </thead>
            <tbody>
            {data.getUsers().map((user: { id: number, name: string, role: string, timestampLastLogin: string }) => (
                <tr key={user.id}>
                    <td className="admin-table-cell-btn">{user.id}</td>
                    <td className="admin-table-cell">{user.name}</td>
                    <td className="admin-table-cell">{user.role}</td>
                    <td className="admin-table-cell">{user.timestampLastLogin}</td>
                </tr>
            ))}
            </tbody>
        </table>
    )
}