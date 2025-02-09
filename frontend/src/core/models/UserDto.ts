export interface UserDto {
    id: number;
    username: string;
    email: string;
    active: boolean;
    roles: string[];
}