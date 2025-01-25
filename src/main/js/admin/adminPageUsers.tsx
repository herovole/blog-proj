import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnits} from "./fragment/atomic/tagselectingform/tagUnits";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";
import {TagService} from "../service/tags/tagService";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";
import {AuthService} from "../service/auth/authService";
import {SearchAdminUserOutput} from "../service/auth/searchAdminUserOutput";
import {AdminUsers} from "./fragment/user/adminUsers";

export const AdminPageUsers: React.FC = () => {
    const tagService: TagService = new TagService();
    const authService: AuthService = new AuthService();
    const [roles, setRoles] = React.useState<TagUnits>(TagUnits.empty());
    const [users, setUsers] = React.useState<SearchAdminUserOutput>(SearchAdminUserOutput.empty());
    const [refresh, setRefresh] = React.useState(false);

    const load = async (): Promise<void> => {

        const rolesOutput: SearchTagsOutput = await tagService.searchRoles();
        setRoles(rolesOutput.getTagUnits);

        const usersOutput: SearchAdminUserOutput = await authService.searchAdminUser();
        setUsers(usersOutput);
    };
    useEffect(() => {
        load().then(r => {
            console.log(r);
        });
    }, [refresh]);

    const reload = () => {
        setRefresh(r => !r);
    }

    if (roles.isEmpty() || users.isEmpty()) {
        return (
            <AdminBasicLayout>
                <div>Loading...</div>
            </AdminBasicLayout>
        );
    } else {
        return (
            <AdminBasicLayout>
                <AdminUsers data={users} roles={roles} reload={reload}/>
            </AdminBasicLayout>
        );
    }
};
