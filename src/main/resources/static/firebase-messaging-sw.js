// Firebase Service Worker for Background Push Notifications
// 이 파일은 반드시 웹사이트 루트에 위치해야 합니다

importScripts('https://www.gstatic.com/firebasejs/10.7.1/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/10.7.1/firebase-messaging-compat.js');

let messaging = null;

// 서버에서 Firebase 설정 가져와서 초기화
async function initializeFirebase() {
    try {
        const response = await fetch('/notification/firebase-config');
        if (!response.ok) throw new Error('Firebase 설정 로드 실패');

        const config = await response.json();

        // Firebase 초기화
        firebase.initializeApp({
            apiKey: config.apiKey,
            authDomain: config.authDomain,
            projectId: config.projectId,
            storageBucket: config.storageBucket,
            messagingSenderId: config.messagingSenderId,
            appId: config.appId
        });

        messaging = firebase.messaging();

        // 백그라운드 메시지 수신 처리
        messaging.onBackgroundMessage((payload) => {
            console.log('[firebase-messaging-sw.js] 백그라운드 메시지 수신:', payload);

            const notificationTitle = payload.notification?.title || '새 메시지';
            const notificationOptions = {
                body: payload.notification?.body || '새로운 메시지가 도착했습니다.',
                icon: '/icon-192x192.png',
                badge: '/badge-72x72.png',
                tag: payload.data?.roomId || 'chat-notification',
                data: payload.data,
                vibrate: [200, 100, 200],
                requireInteraction: true,
                actions: [
                    { action: 'open', title: '열기' },
                    { action: 'close', title: '닫기' }
                ]
            };

            return self.registration.showNotification(notificationTitle, notificationOptions);
        });

        console.log('[firebase-messaging-sw.js] Firebase 초기화 완료');
    } catch (error) {
        console.error('[firebase-messaging-sw.js] Firebase 초기화 실패:', error);
    }
}

// 알림 클릭 처리
self.addEventListener('notificationclick', (event) => {
    console.log('[firebase-messaging-sw.js] 알림 클릭:', event);

    event.notification.close();

    if (event.action === 'close') {
        return;
    }

    // 채팅방으로 이동
    const roomId = event.notification.data?.roomId;
    const urlToOpen = roomId ? `/chat?roomId=${roomId}` : '/chat';

    event.waitUntil(
        clients.matchAll({ type: 'window', includeUncontrolled: true })
            .then((windowClients) => {
                // 이미 열린 창이 있으면 포커스
                for (let client of windowClients) {
                    if (client.url.includes('/chat') && 'focus' in client) {
                        return client.focus();
                    }
                }
                // 없으면 새 창 열기
                if (clients.openWindow) {
                    return clients.openWindow(urlToOpen);
                }
            })
    );
});

// Service Worker 설치
self.addEventListener('install', (event) => {
    console.log('[firebase-messaging-sw.js] Service Worker 설치됨');
    event.waitUntil(initializeFirebase());
    self.skipWaiting();
});

// Service Worker 활성화
self.addEventListener('activate', (event) => {
    console.log('[firebase-messaging-sw.js] Service Worker 활성화됨');
    event.waitUntil(clients.claim());
});
