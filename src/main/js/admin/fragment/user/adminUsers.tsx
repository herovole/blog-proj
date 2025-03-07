import React from "react";
import {SearchAdminUserOutput} from "../../../service/auth/searchAdminUserOutput";
import {AdminUsersModal} from "./adminUsersModal";
import {TagUnits} from "../atomic/tagselectingform/tagUnits";


type AdminUsersType = {
    data: SearchAdminUserOutput;
    roles: TagUnits;
    reload: () => void;
}


export const AdminUsers: React.FC<AdminUsersType> = ({data, roles, reload}) => {

    return (
        <div className="main-area-frame">
            <div className="main-area">
                <AdminUsersModal
                    label={"New User"}
                    roles={roles}
                    reload={reload}
                />
                <table className="admin-table">
                    <thead>
                    <tr>
                        <th className="admin-table-header-cell">ID</th>
                        <th className="admin-table-header-cell">Name</th>
                        <th className="admin-table-header-cell">Role</th>
                        <th className="admin-table-header-cell">Last Login</th>
                        <th className="admin-table-header-cell">Operation</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.getUsers().map((user: {
                        id: number,
                        name: string,
                        role: string,
                        timestampLastLogin: string
                    }) => (
                        <tr key={user.id}>
                            <td className="admin-table-cell-btn">{user.id}</td>
                            <td className="admin-table-cell">{user.name}</td>
                            <td className="admin-table-cell">{user.role}</td>
                            <td className="admin-table-cell">{user.timestampLastLogin}</td>
                            <td className="admin-table-cell">
                                <AdminUsersModal label={"Update User"} roles={roles} reload={reload} user={user}/>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}