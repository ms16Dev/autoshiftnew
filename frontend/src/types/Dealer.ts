// Define CarListDto interface
export interface Dealer {
    id: string;
    username: string;
    complete: boolean;
    dealer: boolean;
    coverUrl: string;
    avatarUrl: string;
    name: string;
    subtitle: string;
    info: string;
    country: string;
    location: string;
    contact: string[];
    likeCount: number;
    createdDate: number;
    lastModifiedDate: number;
}