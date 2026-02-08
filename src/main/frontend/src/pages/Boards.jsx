import { useEffect, useState } from 'react'

export default function Boards() {
  const [message, setMessage] = useState('');

  useEffect(() => {
    const tryBoard = async () => {
      try {
        const response = await fetch('/api/board/hello', {
          method: 'GET',
          credentials: "include",
        })

        const data = await response.text()
        setMessage(data)
      } catch (error) {
        console.error('Error logging in:', error)
        setMessage('Login failed')
      }
    };

    tryBoard();
  }, []);

  return (
    <div className="boards-container">
      {message && <p>{message}</p>}
    </div>
  )
}
