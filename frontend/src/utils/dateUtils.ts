export function formatRelativeDate(dateInput: string | number | Date, lang: 'en' | 'ar' = 'en'): string {
    const createdDate = new Date(dateInput); // Date handles number timestamps just fine
    const now = new Date();

    if (isNaN(createdDate.getTime())) {
        return lang === 'ar' ? 'تاريخ غير صالح' : 'Invalid date';
    }

    const diffInMs = now.getTime() - createdDate.getTime();
    const diffInMinutes = Math.floor(diffInMs / (1000 * 60));
    const diffInHours = Math.floor(diffInMinutes / 60);
    const diffInDays = Math.floor(diffInHours / 24);

    if (diffInDays >= 1) {
        return lang === 'ar'
            ? `قبل ${diffInDays} ${diffInDays === 1 ? 'يوم' : 'أيام'}`
            : `${diffInDays} ${diffInDays === 1 ? 'day' : 'days'} ago`;
    } else if (diffInHours >= 1) {
        return lang === 'ar'
            ? `منذ ${diffInHours} ${diffInHours === 1 ? 'ساعة' : 'ساعات'}`
            : `${diffInHours} ${diffInHours === 1 ? 'hour' : 'hours'} ago`;
    } else if (diffInMinutes >= 1) {
        return lang === 'ar'
            ? `منذ ${diffInMinutes} ${diffInMinutes === 1 ? 'دقيقة' : 'دقائق'}`
            : `${diffInMinutes} ${diffInMinutes === 1 ? 'minute' : 'minutes'} ago`;
    } else {
        return lang === 'ar' ? 'الآن' : 'Just now';
    }
}
